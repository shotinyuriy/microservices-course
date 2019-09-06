package com.gridu.microservice.shoppingcart.rest.model;

import com.gridu.microservice.rest.validation.ErrorResponse;

import java.util.List;

public class TaxesCalculationResponse {

	private TaxesCalculationOrder order;
	private List<ErrorResponse> errorResponses;

	public TaxesCalculationOrder getOrder() {
		return order;
	}

	public void setOrder(TaxesCalculationOrder order) {
		this.order = order;
	}

	public List<ErrorResponse> getErrorResponses() {
		return errorResponses;
	}

	public void setErrorResponses(List<ErrorResponse> errorResponses) {
		this.errorResponses = errorResponses;
	}
}
