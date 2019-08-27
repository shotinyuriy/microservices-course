package com.gridu.microservices.productcatalog.validation;

import com.gridu.microservices.productcatalog.model.ProductCategory;
import com.gridu.microservices.productcatalog.validation.annotation.ExistingCategory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * Created by anasimijonovic on 8/22/19.
 */
public class ExistingCategoryValidator implements ConstraintValidator<ExistingCategory, String> {
    private static String ENTITY_NAME = "category.";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<ProductCategory> productCategory = GlobalDaoHolder.getProductCategoryRepository().findByName(value);
        if (!productCategory.isPresent() || StringUtils.isEmpty(productCategory.get().getId())) {
            context.disableDefaultConstraintViolation();
            String messageTemplate = ENTITY_NAME + context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();

            return false;
        }

        return true;
    }
}
