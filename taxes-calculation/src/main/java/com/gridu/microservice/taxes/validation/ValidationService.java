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

	private ValidatorFactory factory;

	public ValidationService() {
		initialize();
	}

	private void initialize() {
		if (factory == null) {
			synchronized (this) {
				if (factory == null) {
					factory = Validation.buildDefaultValidatorFactory();
				}
			}
		}
	}

	/**
	 * NOTE: probably this method could throw an ConstraintViolationException or our custom validation exception
	 * List of ErrorResponse is returned for the example simplicity
	 * @param obj - an object to validate
	 * @param validationGroups
	 * @param <T> NOTE: use of generics is not necessary here currently,
	 *              but it is used to comply with the validate(T, Class...) method signature of Validator
	 * @return
	 */
	public <T> List<ErrorResponse> validate(T obj, Class<?>... validationGroups) {
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, validationGroups);

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
