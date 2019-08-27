package com.gridu.microservices.productcatalog.rest.transformer;


import com.gridu.microservices.productcatalog.exception.ErrorResponse;
import com.gridu.microservices.productcatalog.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationResultTransformer {

	public List<ErrorResponse> provideValidationErrorResponse(List<ValidationResult> validationResults) {
		List<ErrorResponse> responseList = new ArrayList<>();
		for (ValidationResult validationResult : validationResults) {
			ErrorResponse errorResponse = new ErrorResponse(validationResult.getErrorCode(),
					validationResult.getValue());
			responseList.add(errorResponse);
		}
		return responseList;
	}
}
