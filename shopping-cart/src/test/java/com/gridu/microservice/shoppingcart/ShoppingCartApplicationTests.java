package com.gridu.microservice.shoppingcart;

import com.gridu.microservice.shoppingcart.data.model.Address;
import com.gridu.microservice.shoppingcart.data.model.ShoppingCart;
import com.gridu.microservice.shoppingcart.rest.ShoppingCartRestResource;
import com.gridu.microservice.shoppingcart.rest.model.CommerceItemRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartApplicationTests {

	@Autowired
	private ShoppingCartRestResource shoppingCartRestResource;

	@Test
	public void testGetCartCurrent() {
		ResponseEntity<Object> responseEntity = shoppingCartRestResource.getShoppingCart();

		Assert.assertNotNull(responseEntity.getBody());
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertTrue(responseEntity.getBody() instanceof ShoppingCart);

		ShoppingCart shoppingCart = (ShoppingCart)responseEntity.getBody();
		Assert.assertTrue(shoppingCart.getCommerceItems().isEmpty());

		Assert.assertEquals(0d, shoppingCart.getPriceInfo().getCommerceItemTotal(), 0.0);
		Assert.assertEquals(0d, shoppingCart.getPriceInfo().getTaxesTotal(), 0.0);
		Assert.assertEquals(0d, shoppingCart.getPriceInfo().getTotal(), 0.0);

		// ADD REQUEST
		CommerceItemRequest commerceItemRequest = new CommerceItemRequest();
		commerceItemRequest.setSkuId("26ebf7a0-247d-4c4b-9605-d5872c583c52");
		commerceItemRequest.setQuantity(2);

		ResponseEntity<Object> responseEntityAdd = shoppingCartRestResource.addCommerceItem(commerceItemRequest);
		Assert.assertEquals(HttpStatus.CREATED, responseEntityAdd.getStatusCode());
		Assert.assertNotNull(responseEntityAdd.getBody());

		Assert.assertTrue(responseEntityAdd.getBody() instanceof ShoppingCart);
		shoppingCart = (ShoppingCart)responseEntityAdd.getBody();
		Assert.assertEquals(1, shoppingCart.getCommerceItems().size());
		Assert.assertTrue(shoppingCart.getPriceInfo().getCommerceItemTotal() > 0);
		Assert.assertTrue(shoppingCart.getPriceInfo().getTotal() > 0);
		Assert.assertNull(shoppingCart.getShippingAddress());

		// APPLY SHIPPING ADDRESS
		Address address = new Address();
		address.setState("CA");
		ResponseEntity<Object> responseEntityApplyAddress = shoppingCartRestResource.addShippingAddress(address);
		Assert.assertEquals(HttpStatus.CREATED, responseEntityApplyAddress.getStatusCode());
		Assert.assertNotNull(responseEntityApplyAddress.getBody());
		Assert.assertTrue(responseEntityApplyAddress.getBody() instanceof ShoppingCart);
		shoppingCart = (ShoppingCart)responseEntityApplyAddress.getBody();

		Assert.assertEquals(1, shoppingCart.getCommerceItems().size());
		Assert.assertTrue(shoppingCart.getPriceInfo().getCommerceItemTotal() > 0);
		Assert.assertTrue(shoppingCart.getPriceInfo().getTotal() > 0);
		Assert.assertTrue(shoppingCart.getPriceInfo().getTaxesTotal() > 0);
		Assert.assertNotNull(shoppingCart.getShippingAddress());

		// DELETE COMMERCE ITEM
		String commerceItemIdToDelete = shoppingCart.getCommerceItems().get(0).getId();
		ResponseEntity<Object> responseEntityDelete = shoppingCartRestResource.deleteCommerceItem(commerceItemIdToDelete);

		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntityDelete.getStatusCode());
		Assert.assertNull(responseEntityDelete.getBody());
	}

}
