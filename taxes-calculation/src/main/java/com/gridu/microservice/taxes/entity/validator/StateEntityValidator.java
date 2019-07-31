package com.gridu.microservice.taxes.entity.validator;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.validation.annotation.ValidStateEntity;

@Component
public class StateEntityValidator implements ConstraintValidator<ValidStateEntity, State> {

	private Validator validator = getValidator();
	
	
	@Override
	public boolean isValid(State state, ConstraintValidatorContext context) {
		//one more validator for statRule
		//use both validators in request
		//check what with collected constraint violations inside context
		boolean valid;

		Set<ConstraintViolation<State>> validation = validator.validate(state, State.class);
		if(validation.isEmpty()) {
			valid = true;
		} else {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Msg template").addConstraintViolation();
			valid = false;
		}
		return valid;
	}

	private Validator getValidator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}
}
