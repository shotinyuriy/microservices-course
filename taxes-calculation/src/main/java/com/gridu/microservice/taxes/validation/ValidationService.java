package com.gridu.microservice.taxes.validation;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
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
	 * NOTE: probably this method could throw an ConstraintViolationException or our
	 * custom validation exception List of ValidationResult is returned for the
	 * example simplicity
	 * 
	 * @param obj              - an object to validate
	 * @param validationGroups
	 * @param <T>              NOTE: use of generics is not necessary here
	 *                         currently, but it is used to comply with the
	 *                         validate(T, Class...) method signature of Validator
	 * @return
	 */
	public <T> List<ValidationResult> validate(T obj, Class<?>... validationGroups) {
		List<ValidationResult> validationResults;
		Set<ConstraintViolation<T>> constraintViolations = factory.getValidator().validate(obj, validationGroups);
		validationResults = convertToValidationResult(constraintViolations);

		return validationResults;
	}

	public static <T> List<ValidationResult> convertToValidationResult(Set<ConstraintViolation<T>> violations) {
		return violations.stream().map(violation -> {
			String errorCode = ValidationErrorType.ERROR + "." + violation.getMessage();
			Object value = violation.getInvalidValue();
			Path propertyPath = violation.getPropertyPath(); //store inside validation result

			return new ValidationResult(errorCode, value);
		}).collect(Collectors.toList());
	}
}
