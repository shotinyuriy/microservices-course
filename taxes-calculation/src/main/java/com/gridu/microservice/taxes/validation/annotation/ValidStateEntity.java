package com.gridu.microservice.taxes.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.gridu.microservice.taxes.entity.validator.StateEntityValidator;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER}) //check what should be applied
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StateEntityValidator.class)
@Documented
public @interface ValidStateEntity {

	 String message() default "State not valid.";
	 /*just some boilerplate code to conforms to the Spring standards*/
	 Class<?>[] groups() default {};
	 Class<? extends Payload>[] payload() default {};
}
