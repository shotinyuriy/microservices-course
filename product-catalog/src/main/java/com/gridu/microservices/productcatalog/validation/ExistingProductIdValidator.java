package com.gridu.microservices.productcatalog.validation;

import com.gridu.microservices.productcatalog.model.Product;
import com.gridu.microservices.productcatalog.validation.annotation.ExistingProductId;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * Created by anasimijonovic on 8/21/19.
 */
public class ExistingProductIdValidator implements ConstraintValidator<ExistingProductId, Long> {
    private static String ENTITY_NAME = "product.";

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<Product> product = GlobalDaoHolder.getProductRepository().findById(value);
        if (!product.isPresent() || StringUtils.isEmpty(product.get().getId())) {
            context.disableDefaultConstraintViolation();
            String messageTemplate = ENTITY_NAME + context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();

            return false;
        }

        return true;
    }
}
