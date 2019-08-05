package com.gridu.microservice.taxes.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;
import com.gridu.microservice.taxes.validation.annotation.InvalidStateCode;

public class StateCodeValidator implements ConstraintValidator<InvalidStateCode, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		State state = GlobalDaoHolder.getStateDao().findByCode(value);
		if (state == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addConstraintViolation();
			return false;
		}
		return true;
	}

}
