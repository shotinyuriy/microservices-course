package com.gridu.microservice.taxes.validation;

import com.gridu.microservice.taxes.model.TaxCategory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService {

	public ValidationService() {

	}

	public <T> List<ErrorResponse> validate(T obj, Class<?>... classes) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, classes);

		return convertToErrorResponses(constraintViolations);
	}

	public static <T> List<ErrorResponse> convertToErrorResponses(Set<ConstraintViolation<T>> violations) {
		return violations.stream()
			.map(violation -> {
				String errorCode = violation.getPropertyPath().toString() + "." + violation.getMessage();
				Object value = violation.getInvalidValue();
				return new ErrorResponse(errorCode, value);
			})
			.collect(Collectors.toList());
	}
}
