package com.gridu.microservices.productcatalog.validation.annotation;

import com.gridu.microservices.productcatalog.validation.SkuNotEmptyValidator;
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
@Constraint(validatedBy = SkuNotEmptyValidator.class)
public @interface SkuNotEmpty {
    String message() default ValidationErrorType.EMPTY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
