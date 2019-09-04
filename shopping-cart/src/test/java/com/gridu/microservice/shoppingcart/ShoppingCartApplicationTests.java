package com.gridu.microservice.shoppingcart;

import com.gridu.microservice.shoppingcart.controller.ShoppingCartController;
import com.gridu.microservice.shoppingcart.model.CommerceItem;
import com.gridu.microservice.shoppingcart.model.ItemRequestModel;
import com.gridu.microservice.shoppingcart.model.ShippingAddressModel;
import com.gridu.microservice.shoppingcart.model.ShoppingCartModel;
import com.gridu.microservice.shoppingcart.service.TaxesCalculationItemsModel;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartApplicationTests {

	@Autowired
	private ShoppingCartController shoppingCartController;

	@Before
	public void setUp() {
		assertNotNull(shoppingCartController);
	}

	@Test
	public void shoppingCartController_addItemToCart() {
		Long skuId = 149l;
		int quantity = 3;

		ItemRequestModel itemRequestModel = new ItemRequestModel();
		itemRequestModel.setSkuId(skuId);
		itemRequestModel.setQuantity(quantity);

		int oldSize = shoppingCartController.getItemsFromCart().getBody().getCommerceItems().size();

		// ADD
		shoppingCartController.addItemToCart(itemRequestModel);

		// GET
		List<CommerceItem> items = shoppingCartController.getItemsFromCart().getBody().getCommerceItems();
		Optional<CommerceItem> addedCommerceItem = items.stream().filter(item -> item.getSkuId().equals(skuId)).findFirst();

		assertTrue("Sku not added to cart", addedCommerceItem.isPresent());
		assertTrue("Cart does not have quantity needed", addedCommerceItem.get().getQuantity() == quantity);

		int newSize = shoppingCartController.getItemsFromCart().getBody().getCommerceItems().size();

		assertTrue("Cart does not have size needed", newSize == oldSize + 1);
	}

	@Test
	public void shoppingCartController_deleteItemFromCart() {
		Long skuId = 149l;
		int quantity = 3;

		ItemRequestModel itemRequestModel = new ItemRequestModel();
		itemRequestModel.setSkuId(skuId);
		itemRequestModel.setQuantity(quantity);

		// ADD
		shoppingCartController.addItemToCart(itemRequestModel);

		// GET
		List<CommerceItem> items = shoppingCartController.getItemsFromCart().getBody().getCommerceItems();
		Optional<CommerceItem> addedCommerceItem = items.stream().filter(item -> item.getSkuId().equals(skuId)).findFirst();

		assertTrue("Sku not added to cart", addedCommerceItem.isPresent());
		assertTrue("Cart does not have quantity needed", addedCommerceItem.get().getQuantity() == quantity);

		// DELETE
		ResponseEntity<?> responseDelete = shoppingCartController.deleteItemFromCart(addedCommerceItem.get().getId());

		items = shoppingCartController.getItemsFromCart().getBody().getCommerceItems();
		addedCommerceItem = items.stream().filter(item -> item.getSkuId().equals(skuId)).findFirst();

		assertFalse("Sku not deleted from cart", addedCommerceItem.isPresent());
		assertTrue("Wrong status code", responseDelete.getStatusCode().equals(HttpStatus.NO_CONTENT));
	}

	@Test
	public void setShoppingCartController_shippingAddressSet() {
		ShippingAddressModel.ShippingAddress address = new ShippingAddressModel.ShippingAddress();
		address.setState("AZ");

		// adding sku with product id and mocking response for taxes fot it
		Long skuId = 149l; // product id is 148
		int quantity = 3;

		ItemRequestModel itemRequestModel = new ItemRequestModel();
		itemRequestModel.setSkuId(skuId);
		itemRequestModel.setQuantity(quantity);

		shoppingCartController.addItemToCart(itemRequestModel);

		ShippingAddressModel request = new ShippingAddressModel();
		request.setShippingAddress(address);

		// ADD
		shoppingCartController.addShippingAddress(request);

		// GET
		ResponseEntity<?> response = shoppingCartController.getItemsFromCart();

		assertTrue("Response not of write type", response.getBody() instanceof ShoppingCartModel);
		assertTrue("Shipping address not set", ((ShoppingCartModel) response.getBody()).getShippingAddress().equals(address));

		assertTrue("Taxes total not calculated well", ((ShoppingCartModel) response.getBody()).getPriceInfo().getTaxes() == 10.8);
	}

	@Ignore
	@Test
	public void setShoppingCartController_shippingAddressSetMockTaxesService() {
		TaxesCalculationItemsModel mockedResponse = new TaxesCalculationItemsModel();

		Double mockedTaxTotal = 10.0;

		// adding sku with product id and mocking response for taxes fot it
		Long skuId = 149l; // product id is 148
		int quantity = 3;

		ItemRequestModel itemRequestModel = new ItemRequestModel();
		itemRequestModel.setSkuId(skuId);
		itemRequestModel.setQuantity(quantity);

		shoppingCartController.addItemToCart(itemRequestModel);
		//****************

		TaxesCalculationItemsModel.TaxCalculationItemModel taxCalculationItemModel = new TaxesCalculationItemsModel.TaxCalculationItemModel();
		taxCalculationItemModel.setTaxes(new TaxesCalculationItemsModel.TaxesModel(mockedTaxTotal.toString()));
		taxCalculationItemModel.setId("148");
		mockedResponse.setItems(Collections.singletonList(taxCalculationItemModel));

//		Mockito.when(taxesCalculationServiceMock.getTaxesInfo(any(TaxesCalculationItemsModel.class))).thenReturn(mockedResponse);

		ShippingAddressModel.ShippingAddress address = new ShippingAddressModel.ShippingAddress();
		address.setState("AZ");

		ShippingAddressModel request = new ShippingAddressModel();
		request.setShippingAddress(address);

		// ADD
		shoppingCartController.addShippingAddress(request);

		// GET
		ResponseEntity<?> response = shoppingCartController.getItemsFromCart();

		assertTrue("Response not of write type", response.getBody() instanceof ShoppingCartModel);
		assertTrue("Shipping address not set", ((ShoppingCartModel) response.getBody()).getShippingAddress().equals(address));

		assertTrue("Taxes not calculated well", ((ShoppingCartModel) response.getBody()).getPriceInfo().getTaxes() == mockedTaxTotal);
	}
}
