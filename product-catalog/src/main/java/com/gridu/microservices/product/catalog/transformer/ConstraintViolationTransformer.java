package com.gridu.microservices.product.catalog.transformer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.stereotype.Component;

import com.gridu.microservices.product.catalog.validation.ValidationConstants;
import com.gridu.microservices.product.catalog.validation.ValidationResult;

@Component
public class ConstraintViolationTransformer {

	public <T> List<ValidationResult> getValidationResult(Set<ConstraintViolation<T>> violations) {
		return violations.stream().map(violation -> {
			String errorCode = String.format("%s.%s.%s", ValidationConstants.ERROR, violation.getPropertyPath(),
					violation.getMessage());
			return new ValidationResult(errorCode, violation.getInvalidValue(), violation.getPropertyPath().toString());
		}).collect(Collectors.toList());
	}
}
