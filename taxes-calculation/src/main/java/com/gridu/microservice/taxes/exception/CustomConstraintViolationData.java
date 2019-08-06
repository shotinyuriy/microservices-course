package com.gridu.microservice.taxes.exception;

/**
 * 
 * Holds information for a single constraint violation
 *
 */
public class CustomConstraintViolationData {

	private String message;
	private Object value;
	
	public CustomConstraintViolationData() {
		
	}
	public CustomConstraintViolationData(String string, String stateCode) {
		this.message = string;
		this.value = stateCode;
	}

	public String getMessage() {
		return message;
	} 
	
	public void setErrorMessage(String message) {
		this.message = message;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
}
