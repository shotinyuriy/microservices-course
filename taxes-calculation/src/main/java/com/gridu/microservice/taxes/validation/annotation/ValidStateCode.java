package com.gridu.microservice.taxes.validation.annotation;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.gridu.microservice.rest.validation.ValidationErrorType;
import com.gridu.microservice.taxes.entity.validator.StateCodeValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;



@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = StateCodeValidator.class)
@Documented
public @interface ValidStateCode {
	String message() default ValidationErrorType.NOT_FOUND;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
