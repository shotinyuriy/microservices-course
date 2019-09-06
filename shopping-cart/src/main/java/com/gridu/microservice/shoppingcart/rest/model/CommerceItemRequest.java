package com.gridu.microservice.shoppingcart.rest.model;

public class CommerceItemRequest {

	private String skuId;
	private int quantity;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
