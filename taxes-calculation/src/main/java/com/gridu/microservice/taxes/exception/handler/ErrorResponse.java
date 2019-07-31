package com.gridu.microservice.taxes.exception.handler;

public class ErrorResponse {

	private String errorCode;
	private String value;
	
	public ErrorResponse(String errorCode, String value) {
		this.errorCode = errorCode;
		this.value = value;
	}
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
