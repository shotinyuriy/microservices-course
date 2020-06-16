package com.gridu.microservices.product.catalog.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gridu.microservices.product.catalog.exceptions.UniqueObjectViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UniqueObjectViolationException.class)
	protected ResponseEntity<Object> handleUniqueObjectViolation(UniqueObjectViolationException exception) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
	}
}
