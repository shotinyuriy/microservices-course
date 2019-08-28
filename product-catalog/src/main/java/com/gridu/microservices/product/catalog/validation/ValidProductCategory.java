package com.gridu.microservices.product.catalog.validation;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ProductCategoryValidator.class)
@Documented
public @interface ValidProductCategory {
	String message() default "error";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
