package com.gridu.microservice.taxes.exception;

public class ConstraintViolation {

	private String message; //set snippet
	private Object value; //set code
	
	public ConstraintViolation() {
		
	}
	public ConstraintViolation(String string, String stateCode) {
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
