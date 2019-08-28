package com.gridu.microservices.productcatalog.validation.annotation;

import com.gridu.microservices.productcatalog.validation.ExistingProductIdValidator;
import com.gridu.microservices.productcatalog.validation.ValidationErrorType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anasimijonovic on 8/21/19.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingProductIdValidator.class)
public @interface ExistingProductId {

    String message() default ValidationErrorType.NOT_FOUND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
