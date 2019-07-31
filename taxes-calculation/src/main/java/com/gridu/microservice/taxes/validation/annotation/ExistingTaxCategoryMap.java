package com.gridu.microservice.taxes.validation.annotation;

import com.gridu.microservice.taxes.validation.ValidationErrorType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates if every key in the Map&lt;String, ?&gt; is a taxCategory name existing in the DataBase
 */
@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistingTaxCategoryMapValidator.class)
@Documented
public @interface ExistingTaxCategoryMap {
	String message() default ValidationErrorType.INVALID;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
