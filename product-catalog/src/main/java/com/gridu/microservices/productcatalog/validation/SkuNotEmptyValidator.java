package com.gridu.microservices.productcatalog.validation;

import com.gridu.microservices.productcatalog.rest.model.ProductModel;
import com.gridu.microservices.productcatalog.validation.annotation.SkuNotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * Created by anasimijonovic on 8/22/19.
 */
public class SkuNotEmptyValidator implements ConstraintValidator<SkuNotEmpty, ProductModel> {
    private List<String> categoriesWithSkus = Arrays.asList("electronic device", "clothing");
    private String ENTITY_NAME = "sku.";

    @Override
    public boolean isValid(ProductModel model, ConstraintValidatorContext context) {
        if (categoriesWithSkus.contains(model.getCategory()) && (model.getChildSkus() == null || model.getChildSkus().size() == 0)) {
            context.disableDefaultConstraintViolation();
            String messageTemplate = ENTITY_NAME + context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();

            return false;
        }

        return true;
    }
}
