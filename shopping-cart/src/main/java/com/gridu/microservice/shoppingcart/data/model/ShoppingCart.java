package com.gridu.microservice.shoppingcart.data.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private List<CommerceItem> commerceItems = new ArrayList<>();
	private PriceInfo priceInfo = new PriceInfo();
	private Address shippingAddress;

	public List<CommerceItem> getCommerceItems() {
		return commerceItems;
	}

	public void setCommerceItems(List<CommerceItem> commerceItems) {
		this.commerceItems = commerceItems;
	}

	public PriceInfo getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public CommerceItem findCommerceItem(String commerceItemId) {
		return commerceItems.stream()
			.filter(ci -> ci.getId().equals(commerceItemId))
			.findFirst()
			.orElseGet(() -> null);
	}

	public void addCommerceItem(CommerceItem newCommerceItem) {
		CommerceItem existing = commerceItems.stream()
			.filter(ci -> ci.getSkuId().equals(newCommerceItem.getSkuId()))
			.findFirst()
			.orElseGet(() -> null);

		if (existing != null) {
			existing.setQuantity(existing.getQuantity() + newCommerceItem.getQuantity());
		} else {
			commerceItems.add(newCommerceItem);
		}
	}
}
