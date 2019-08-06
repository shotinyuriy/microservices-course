package com.gridu.microservice.taxes.exception;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;

public class CustomConstraintViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Set<CustomConstraintViolationData> constraintViolations = new HashSet<CustomConstraintViolationData>();
	private HttpStatus status;

	public CustomConstraintViolationException(String message) {
		super(message);
	}

	public CustomConstraintViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomConstraintViolationException(Throwable cause) {
		super(cause);
	}
	
	public void addViolationResult(CustomConstraintViolationData violationResult) {
		constraintViolations.add(violationResult);
	}

	public void addViolationResults(List<CustomConstraintViolationData> violationResults) {
		for (CustomConstraintViolationData valiationResult : violationResults) {
			addViolationResult(valiationResult);
		}
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Set<CustomConstraintViolationData> getViolationResults() {
		return constraintViolations;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
