package com.gridu.microservice.taxes.rest.model;


import com.gridu.microservice.rest.validation.ErrorResponse;
import com.gridu.microservice.taxes.model.TaxesCalculationItemsModel;

import java.util.Collection;

public class TaxesCalculationResponse {

	private TaxesCalculationItemsModel order;
	private Collection<ErrorResponse> errors;

	public TaxesCalculationItemsModel getOrder() {
		return order;
	}

	public void setOrder(TaxesCalculationItemsModel order) {
		this.order = order;
	}

	public Collection<ErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(Collection<ErrorResponse> errors) {
		this.errors = errors;
	}
}
