package com.gridu.microservice.taxes.validation;

import com.gridu.microservice.rest.validation.AbstractValidationService;
import com.gridu.microservice.rest.validation.ValidationResult;
import org.springframework.beans.factory.InitializingBean;
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
public class ValidationService extends AbstractValidationService implements InitializingBean {

	private Validator validator;

	@Autowired
	private ValidatorFactory validatorFactory;

	public ValidatorFactory getValidatorFactory() {
		return validatorFactory;
	}

	protected Validator getValidator() {
		if (validator == null) {
			validator = validatorFactory.getValidator();
		}
		return validator;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		this.validatorFactory = validatorFactory;
	}

	public ValidationService() {
		super();
	}

	@Override
	public <T> Set<ValidationResult> validate(T obj, Class<?>... validationGroups) {
		return validate(getValidator(), obj, validationGroups);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		validator = validatorFactory.getValidator();
	}
}
