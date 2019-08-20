package com.gridu.microservice.taxes.validation.annotation;

import com.gridu.microservice.taxes.entity.validator.ExistingStateCodeValidator;
import com.gridu.microservice.taxes.validation.ValidationErrorType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anasimijonovic on 8/19/19.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingStateCodeValidator.class)
public @interface ExistingStateCode {

    String message() default ValidationErrorType.NOT_FOUND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
