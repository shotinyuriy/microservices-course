package com.gridu.microservices.productcatalog.data.validator.annotation;

import com.gridu.microservice.rest.validation.ValidationErrorType;
import com.gridu.microservices.productcatalog.data.validator.impl.ExistingTaxCategoryNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates if the taxCategory name exists in the DataBase
 */
@Target({FIELD, TYPE_USE, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistingTaxCategoryNameValidator.class)
@Documented
public @interface ExistingTaxCategoryName {

	String message() default ValidationErrorType.INVALID;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
