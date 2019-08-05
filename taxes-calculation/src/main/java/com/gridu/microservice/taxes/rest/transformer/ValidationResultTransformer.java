package com.gridu.microservice.taxes.rest.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gridu.microservice.taxes.exception.handler.ErrorResponse;
import com.gridu.microservice.taxes.validation.ValidationResult;

@Component
public class ValidationResultTransformer {

	public List<ErrorResponse> provideValidationErrorResponse(List<ValidationResult> validationResults) {
		List<ErrorResponse> responseList = new ArrayList<ErrorResponse>();
		for (ValidationResult validationResult : validationResults) {
			ErrorResponse errorResponse = new ErrorResponse(validationResult.getErrorCode(),
					validationResult.getValue());
			responseList.add(errorResponse);
		}
		return responseList;
	}
}
