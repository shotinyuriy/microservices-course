package com.gridu.microservice.rest.validation;

import java.util.Set;
import java.util.stream.Collectors;

public class ErrorResponseTransformer {

	public Set<ErrorResponse> fromValidationResults(Set<ValidationResult> validationResults) {
		return validationResults.stream()
			.map(validationResult -> {
				String errorCode = ValidationErrorType.ERROR
					+ "." + validationResult.getPropertyPath().toString()
					+ "." + validationResult.getErrorType();
				return new ErrorResponse(errorCode, validationResult.getPropertyValue());
			})
			.collect(Collectors.toSet());
	}
}
