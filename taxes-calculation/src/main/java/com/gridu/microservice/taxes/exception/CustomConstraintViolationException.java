package com.gridu.microservice.taxes.exception;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;

public class CustomConstraintViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus status;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	private Set<ConstraintViolation> constraintViolations = new HashSet<ConstraintViolation>();

	public CustomConstraintViolationException(String message) {
		super(message);
	}

	public CustomConstraintViolationException(Set<ConstraintViolation> constraintViolations) {
		this.constraintViolations = constraintViolations;
	}
	
	public CustomConstraintViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomConstraintViolationException(Throwable cause) {
		super(cause);
	}

	public void addValidationResult(List<ConstraintViolation> validationResults) {
		for (ConstraintViolation validationResult : validationResults) {
			addValidationResult(validationResult);
		}
	}

	public void addValidationResult(ConstraintViolation validationResult) {
		constraintViolations.add(validationResult);
	}

	public Set<ConstraintViolation> getValidationResults() {
		return constraintViolations;
	}

}
