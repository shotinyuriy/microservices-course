package com.gridu.microservice.taxes.validation.exception;

import java.util.Set;

import com.gridu.microservice.taxes.exception.handler.ErrorResponse;

public class CustomValidationException extends Exception {

	private Set<ErrorResponse> errorResponses;

	public CustomValidationException(Set<ErrorResponse> errorResponses) {
		this.errorResponses = errorResponses;
	}
}
