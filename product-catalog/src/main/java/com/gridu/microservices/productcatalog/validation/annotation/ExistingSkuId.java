package com.gridu.microservices.productcatalog.validation.annotation;

import com.gridu.microservices.productcatalog.validation.ExistingSkuIdValidator;
import com.gridu.microservices.productcatalog.validation.ValidationErrorType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anasimijonovic on 8/27/19.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = ExistingSkuIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingSkuId {
    String message() default ValidationErrorType.NOT_FOUND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
