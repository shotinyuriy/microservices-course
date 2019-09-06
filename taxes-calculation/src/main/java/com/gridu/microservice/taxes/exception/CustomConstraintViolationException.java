package com.gridu.microservice.taxes.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.gridu.microservice.rest.validation.ErrorResponse;

public class CustomConstraintViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Collection<ErrorResponse> constraintViolations;
	private HttpStatus status;

	
	public CustomConstraintViolationException(String message) {
		this(message, new ArrayList<ErrorResponse>());
	}
	
	public CustomConstraintViolationException(String message, List<ErrorResponse> violationResults) {
		this(message, violationResults, HttpStatus.NOT_FOUND);
	}

	public CustomConstraintViolationException(String message, Collection<ErrorResponse> violationResults, HttpStatus status) {
		super(message);
		this.constraintViolations = violationResults;
		this.status = status;
	}
	
	public void addViolationResult(ErrorResponse violationResult) {
		constraintViolations.add(violationResult);
	}

	public void addViolationResults(List<ErrorResponse> violationResults) {
		for (ErrorResponse valiationResult : violationResults) {
			addViolationResult(valiationResult);
		}
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Collection<ErrorResponse> getViolationResults() {
		return constraintViolations;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
