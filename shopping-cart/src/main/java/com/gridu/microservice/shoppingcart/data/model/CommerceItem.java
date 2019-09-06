package com.gridu.microservice.shoppingcart.data.model;

public class CommerceItem {

	private String id;
	private String productId;
	private String skuId;
	private String category;
	private double price;
	private int quantity;
	private double totalPrice;
	private TaxesInfo taxes = new TaxesInfo();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSkuId() {
		return skuId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public TaxesInfo getTaxes() {
		return taxes;
	}

	public void setTaxes(TaxesInfo taxes) {
		this.taxes = taxes;
	}
}
