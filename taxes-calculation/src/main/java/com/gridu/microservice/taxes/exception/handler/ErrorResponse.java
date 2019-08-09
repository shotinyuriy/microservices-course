package com.gridu.microservice.taxes.exception.handler;

public class ErrorResponse {

	private String errorCode;
	private Object value;

	public ErrorResponse() {
	}

	public ErrorResponse(String errorCode, Object value) {
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ErrorResponse that = (ErrorResponse) o;

		if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
		return value != null ? value.equals(that.value) : that.value == null;
	}

	@Override
	public int hashCode() {
		int result = errorCode != null ? errorCode.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
