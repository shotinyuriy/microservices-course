package com.gridu.microservices.productcatalog.validation;

import com.gridu.microservices.productcatalog.model.Product;
import com.gridu.microservices.productcatalog.validation.annotation.UniqueProductName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * Created by anasimijonovic on 8/22/19.
 */
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {
    private static String ENTITY_NAME = "product.";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Product> product = GlobalDaoHolder.getProductService().getProductsByName(value);
        if (product.isPresent()) {
            context.disableDefaultConstraintViolation();
            String messageTemplate = ENTITY_NAME + context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();

            return false;
        }

        return true;
    }
}
