package com.gridu.microservice.taxes.validation;

public class ValidationResult {

	private String errorCode; //error type + property name/path
	private Object value; //value which is wrong
	
	public ValidationResult(String errorCode, Object value) {
		this.errorCode = errorCode;
		this.value = value;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		
		ValidationResult that = (ValidationResult) obj;
		
		if(errorCode != null ? !errorCode.equals(that.getErrorCode()) : that.errorCode != null) return false;
		return value != null ? value.equals(that.value) : that.value == null;
	}
	
	@Override
	public int hashCode() {
		int result = errorCode != null ? errorCode.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
