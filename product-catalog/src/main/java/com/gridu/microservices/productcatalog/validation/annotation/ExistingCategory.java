package com.gridu.microservices.productcatalog.validation.annotation;

import com.gridu.microservices.productcatalog.validation.ExistingCategoryValidator;
import com.gridu.microservices.productcatalog.validation.ValidationErrorType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anasimijonovic on 8/22/19.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingCategoryValidator.class)
public @interface ExistingCategory {
    String message() default ValidationErrorType.INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
