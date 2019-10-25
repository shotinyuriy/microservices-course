package com.gridu.microservice.shoppingcart.rest.validation;

import com.gridu.microservice.rest.validation.AbstractValidationService;
import com.gridu.microservice.rest.validation.ValidationResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class ValidatorService extends AbstractValidationService implements InitializingBean {


	private Validator validator;

	@Autowired
	private ValidatorFactory validatorFactory;

	public ValidatorFactory getValidatorFactory() {
		return validatorFactory;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		this.validatorFactory = validatorFactory;
	}

	@Override
	public <T> Set<ValidationResult> validate(T obj, Class<?>... groups) {
		return validate(validator, obj, groups);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		validator = validatorFactory.getValidator();
	}
}