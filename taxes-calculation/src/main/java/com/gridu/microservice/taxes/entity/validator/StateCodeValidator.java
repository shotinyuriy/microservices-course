package com.gridu.microservice.taxes.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;
import com.gridu.microservice.taxes.validation.annotation.ValidStateCode;

public class StateCodeValidator implements ConstraintValidator<ValidStateCode, String> {

	private static String ENTITY_NAME = "state.";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		State state = GlobalDaoHolder.getStateDao().findByCode(value);
		if (state == null || StringUtils.isEmpty(state.getCode())) {
			context.disableDefaultConstraintViolation();
			String messageTemplate = ENTITY_NAME + context.getDefaultConstraintMessageTemplate();
			context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
			return false;
		}
		return true;
	}

}
