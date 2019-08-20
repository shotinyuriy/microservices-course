package com.gridu.microservice.taxes.entity.validator;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;
import com.gridu.microservice.taxes.validation.annotation.ExistingStateCode;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by anasimijonovic on 8/19/19.
 */
public class ExistingStateCodeValidator implements ConstraintValidator<ExistingStateCode, String> {

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
