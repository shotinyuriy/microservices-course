package com.gridu.microservices.productcatalog.validation;

import com.gridu.microservices.productcatalog.model.Sku;
import com.gridu.microservices.productcatalog.validation.annotation.ExistingSkuId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by anasimijonovic on 8/27/19.
 */
public class ExistingSkuIdValidator implements ConstraintValidator<ExistingSkuId, Long> {
    private static String ENTITY_NAME = "sku.";

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Sku sku = GlobalDaoHolder.getSkuService().getSkuById(value);
        if (sku == null) {
            context.disableDefaultConstraintViolation();
            String messageTemplate = ENTITY_NAME + context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();

            return false;
        }

        return true;
    }
}
