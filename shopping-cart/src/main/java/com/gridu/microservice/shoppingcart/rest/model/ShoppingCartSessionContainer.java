package com.gridu.microservice.shoppingcart.rest.model;

import com.gridu.microservice.shoppingcart.data.model.ShoppingCart;

public class ShoppingCartSessionContainer {

	private ShoppingCart shoppingCart = new ShoppingCart();

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
}
