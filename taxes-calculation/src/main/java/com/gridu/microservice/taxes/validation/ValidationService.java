package com.gridu.microservice.taxes.validation;

import com.gridu.microservice.rest.validation.AbstractValidationService;
import com.gridu.microservice.rest.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService extends AbstractValidationService {

	@Autowired
	private Validator validator;

	public ValidationService() {
		super();
	}

	@Override
	public <T> Set<ValidationResult> validate(T obj, Class<?>... validationGroups) {
		return validate(validator, obj, validationGroups);
	}
}
