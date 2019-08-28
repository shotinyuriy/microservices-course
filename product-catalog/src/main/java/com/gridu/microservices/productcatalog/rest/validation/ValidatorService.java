package com.gridu.microservices.productcatalog.rest.validation;

import com.gridu.microservice.rest.validation.AbstractValidationService;
import com.gridu.microservice.rest.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Set;

@Service
public class ValidatorService extends AbstractValidationService {

	@Autowired
	private Validator validator;

	@Override
	public <T> Set<ValidationResult> validate(T obj, Class<?>... groups) {
		return validate(validator, obj, groups);
	}
}
