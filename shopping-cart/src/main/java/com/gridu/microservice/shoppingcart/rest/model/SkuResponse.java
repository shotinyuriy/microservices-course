package com.gridu.microservice.shoppingcart.rest.model;

import java.io.Serializable;

public class SkuResponse implements Serializable {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
