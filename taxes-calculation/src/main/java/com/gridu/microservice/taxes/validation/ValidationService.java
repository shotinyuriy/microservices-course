package com.gridu.microservice.taxes.validation;

import com.gridu.microservice.rest.validation.AbstractValidationService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService extends AbstractValidationService {

	public ValidationService() {
		super();
	}
}
