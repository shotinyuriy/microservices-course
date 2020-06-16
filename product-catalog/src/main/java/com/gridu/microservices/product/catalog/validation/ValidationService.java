package com.gridu.microservices.product.catalog.validation;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

	private ValidatorFactory factory;

	public ValidationService() {
		init();
	}

	private void init() {
		if (factory == null) {
			synchronized (this) {
				if (factory == null) {
					factory = Validation.buildDefaultValidatorFactory();
				}
			}
		}
	}
	
	public <T> List<ValidationResult> validate(T object, Class<?>... groups) {
		List<ValidationResult> result;
		Set<ConstraintViolation<T>> violations = factory.getValidator().validate(object, groups);
		result = convertToValidationResult(violations);
		return result;
	}

	private <T> List<ValidationResult> convertToValidationResult(Set<ConstraintViolation<T>> violations) {
		// TODO Auto-generated method stub
		return null;
	}
}
