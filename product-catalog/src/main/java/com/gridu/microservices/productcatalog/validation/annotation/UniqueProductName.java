package com.gridu.microservices.productcatalog.validation.annotation;

import com.gridu.microservices.productcatalog.validation.UniqueProductNameValidator;
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
@Constraint(validatedBy = UniqueProductNameValidator.class)
public @interface UniqueProductName {
    String message() default ValidationErrorType.EXISTS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
