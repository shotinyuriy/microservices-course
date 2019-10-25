package com.gridu.microservice.shoppingcart.data.model;

public class PriceInfo {

	private double commerceItemTotal;
	private double taxesTotal;
	private double total;

	public double getCommerceItemTotal() {
		return commerceItemTotal;
	}

	public void setCommerceItemTotal(double commerceItemTotal) {
		this.commerceItemTotal = commerceItemTotal;
	}

	public double getTaxesTotal() {
		return taxesTotal;
	}

	public void setTaxesTotal(double taxesTotal) {
		this.taxesTotal = taxesTotal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
