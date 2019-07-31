package com.gridu.microservice.taxes.validation.exception;

import com.gridu.microservice.taxes.validation.ErrorResponse;

import java.util.Set;

public class CustomValidationException extends Exception {

	private Set<ErrorResponse> errorResponses;

	public CustomValidationException(Set<ErrorResponse> errorResponses) {
		this.errorResponses = errorResponses;
	}
}
