package com.gridu.microservices.productcatalog.rest.model;

public class AddProductResponse {

	private String id;

	public AddProductResponse(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
