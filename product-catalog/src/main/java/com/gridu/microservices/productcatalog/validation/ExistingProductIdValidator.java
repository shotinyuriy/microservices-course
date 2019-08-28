package com.gridu.microservices.productcatalog.validation;

import com.gridu.microservices.productcatalog.model.Product;
import com.gridu.microservices.productcatalog.validation.annotation.ExistingProductId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by anasimijonovic on 8/21/19.
 */
public class ExistingProductIdValidator implements ConstraintValidator<ExistingProductId, Long> {
    private static String ENTITY_NAME = "product.";

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Product product = GlobalDaoHolder.getProductService().getById(value);
        if (product == null) {
            context.disableDefaultConstraintViolation();
            String messageTemplate = ENTITY_NAME + context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();

            return false;
        }

        return true;
    }
}
