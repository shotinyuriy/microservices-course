package com.gridu.microservice.rest.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractValidationService {

	protected ValidatorFactory factory;

	public AbstractValidationService() {
		initialize();
	}

	protected void initialize() {
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
	 * @param obj              - an object to validate
	 * @param validationGroups
	 * @param <T>              NOTE: use of generics is not necessary here
	 *                         currently, but it is used to comply with the
	 *                         validate(T, Class...) method signature of Validator
	 * @return
	 */
	abstract public <T> Set<ValidationResult> validate(T obj, Class<?>... validationGroups);

	/**
	 * NOTE: probably this method could throw an ConstraintViolationException or our
	 * custom validation exception List of ValidationResult is returned for the
	 * example simplicity
	 * @param obj              - an object to validate
	 * @param validationGroups
	 * @param <T>              NOTE: use of generics is not necessary here
	 *                         currently, but it is used to comply with the
	 *                         validate(T, Class...) method signature of Validator
	 * @return
	 */
	public <T> Set<ValidationResult> validate(Validator validator, T obj, Class<?>... validationGroups) {
		Set<ValidationResult> validationResults;
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, validationGroups);
		validationResults = convertToValidationResult(constraintViolations);

		return validationResults;
	}

	static <T> Set<ValidationResult> convertToValidationResult(Set<ConstraintViolation<T>> violations) {
		return violations.stream()
			.map(violation -> {
				return new ValidationResult(
					violation.getPropertyPath(),
					violation.getMessage(),
					violation.getInvalidValue());
			})
			.collect(Collectors.toSet());
	}
}
