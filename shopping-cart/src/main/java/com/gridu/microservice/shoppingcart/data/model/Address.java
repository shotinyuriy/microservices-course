package com.gridu.microservice.shoppingcart.data.model;

import com.gridu.microservice.rest.validation.ValidationErrorType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Address implements Serializable {

	@NotNull(message = ValidationErrorType.MISSING)
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}