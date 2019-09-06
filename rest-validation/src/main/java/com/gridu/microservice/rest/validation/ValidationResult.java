package com.gridu.microservice.rest.validation;

import javax.validation.Path;

public class ValidationResult {

	private Path propertyPath;
	private String errorType;
	private Object propertyValue;

	public ValidationResult(Path propertyPath, String errorType, Object propertyValue) {
		this.propertyPath = propertyPath;
		this.errorType = errorType;
		this.propertyValue = propertyValue;
	}

	public Path getPropertyPath() {
		return propertyPath;
	}

	public void setPropertyPath(Path propertyPath) {
		this.propertyPath = propertyPath;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ValidationResult that = (ValidationResult) o;

		if (propertyPath != null ? !propertyPath.equals(that.propertyPath) : that.propertyPath != null) return false;
		if (errorType != null ? !errorType.equals(that.errorType) : that.errorType != null) return false;
		return propertyValue != null ? propertyValue.equals(that.propertyValue) : that.propertyValue == null;
	}

	@Override
	public int hashCode() {
		int result = propertyPath != null ? propertyPath.hashCode() : 0;
		result = 31 * result + (errorType != null ? errorType.hashCode() : 0);
		result = 31 * result + (propertyValue != null ? propertyValue.hashCode() : 0);
		return result;
	}
}
