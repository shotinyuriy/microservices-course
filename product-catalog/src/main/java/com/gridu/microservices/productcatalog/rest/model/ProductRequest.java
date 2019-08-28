package com.gridu.microservices.productcatalog.rest.model;

import java.util.List;

public class ProductRequest {

	private String name;
	private Double price;
	private String category;
	private List<SkuRequest> childSkus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<SkuRequest> getChildSkus() {
		return childSkus;
	}

	public void setChildSkus(List<SkuRequest> childSkus) {
		this.childSkus = childSkus;
	}
}
