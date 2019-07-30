package com.gridu.microservice.taxes.entity.validator;

public interface EntityValidator<T> {

	boolean isValid(T entity);
}
