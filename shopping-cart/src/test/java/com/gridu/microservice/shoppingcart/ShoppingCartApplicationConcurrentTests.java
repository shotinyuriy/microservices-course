package com.gridu.microservice.shoppingcart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridu.microservice.shoppingcart.data.model.Address;
import com.gridu.microservice.shoppingcart.data.model.CommerceItem;
import com.gridu.microservice.shoppingcart.data.model.ShoppingCart;
import com.gridu.microservice.shoppingcart.rest.ShoppingCartRestResource;
import com.gridu.microservice.shoppingcart.rest.model.CommerceItemRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartApplicationConcurrentTests {

	@Autowired
	private ShoppingCartRestResource shoppingCartRestResource;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetCartCurrent() {

		final String[] skuIds = {"26ebf7a0-247d-4c4b-9605-d5872c583c52",
			"6381e39c-6e23-4fa3-8057-4c0f672f3b3c",
			"e8fdfa24-64d8-4d72-92ab-54fc8692fccb",
			"d5a429a3-a9b0-4352-a4ed-f34eaedeb803"
		};

		final List<AssertionError> assertionErrors = new CopyOnWriteArrayList<>();
		final int N = skuIds.length;

		List<Thread> threads = new ArrayList<>();

		MockHttpSession mockHttpSession = new MockHttpSession();

		for (int i = 0; i < N; i++) {
			final String skuId =  skuIds[i % skuIds.length];
			Runnable task = () -> {

				try {

					ObjectMapper objectMapper = new ObjectMapper();

					MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
						.get("/carts/current")
						.session(mockHttpSession);

					MvcResult mvcResult = mockMvc
						.perform(builder)
						.andExpect(status().isOk())
						.andReturn();

					MockHttpServletResponse response = mvcResult.getResponse();

					Assert.assertNotNull(response);
					Assert.assertNotNull(response.getContentAsString());

////////////////////
					CommerceItemRequest commerceItemRequest = new CommerceItemRequest();
					commerceItemRequest.setSkuId(skuId);
					commerceItemRequest.setQuantity(1);

					String addCommerceItemContent = objectMapper.writeValueAsString(commerceItemRequest);

					MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders
						.post("/carts/current/commerceItems")
						.contentType(MediaType.APPLICATION_JSON)
						.content(addCommerceItemContent)
						.session(mockHttpSession);

					MvcResult mvcResultPost = mockMvc
						.perform(postBuilder)
						.andExpect(status().isCreated())
						.andReturn();

					MockHttpServletResponse responsePost = mvcResultPost.getResponse();

					Assert.assertNotNull(responsePost);

					String addCommerceItemResponse = responsePost.getContentAsString();
					ShoppingCart addCommerceItemShoppingCart = objectMapper.readValue(addCommerceItemResponse, ShoppingCart.class);

					Assert.assertNotNull(addCommerceItemShoppingCart);

////////////////////

					Address address = new Address();
					address.setState("CA");

					String addShippingAddressContent = objectMapper.writeValueAsString(address);

					MockHttpServletRequestBuilder postAddressBuilder = MockMvcRequestBuilders
						.post("/carts/current/shippingAddress")
						.contentType(MediaType.APPLICATION_JSON)
						.content(addShippingAddressContent)
						.session(mockHttpSession);

					MvcResult mvcResultPostAddress = mockMvc
						.perform(postAddressBuilder)
						.andExpect(status().isCreated())
						.andReturn();

					MockHttpServletResponse responsePostAddress = mvcResultPostAddress.getResponse();

					Assert.assertNotNull(responsePostAddress);

					String addAddressResponse = responsePostAddress.getContentAsString();
					ShoppingCart addAddressShoppingCart = objectMapper.readValue(addAddressResponse, ShoppingCart.class);

					Assert.assertNotNull(addAddressShoppingCart);

////////////////////
					Optional<CommerceItem> commerceItem = addAddressShoppingCart.getCommerceItems().stream()
						.filter(ci -> ci.getSkuId().equals(skuId))
						.findFirst();

					if (commerceItem.isPresent()) {
						MockHttpServletRequestBuilder deleteItemBuilder = MockMvcRequestBuilders
							.delete("/carts/current/commerceItems/"+commerceItem.get().getId())
							.session(mockHttpSession);

						MvcResult mvcResultDeleteItem = mockMvc.perform(deleteItemBuilder)
							.andExpect(status().isNoContent())
							.andReturn();

						MockHttpServletResponse responseDeleteItem = mvcResultDeleteItem.getResponse();

						Assert.assertNotNull(responseDeleteItem);
					}

				} catch (java.lang.AssertionError e) {
					assertionErrors.add(e);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			};

			threads.add(new Thread(task));
		}

		mockHttpSession.clearAttributes();

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		assertionErrors.forEach(ae -> ae.printStackTrace());

	}

}
