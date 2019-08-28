package com.gridu.microservices.product.catalog.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ValidationResult {

	private String errorCode;
	
	@JsonIgnore
	private String propertyPath;
	
	@JsonInclude(value = Include.NON_EMPTY)
	private Object value;

	public ValidationResult(String errorCode, Object value) {
		this(errorCode, value, "");
	}

	public ValidationResult(String errorCode, Object value, String propertyPath) {
		this.errorCode = errorCode;
		this.value = value;
		this.propertyPath = propertyPath;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		ValidationResult that = (ValidationResult) obj;

		if (errorCode != null ? !errorCode.equals(that.getErrorCode()) : that.errorCode != null)
			return false;
		if (value != null ? !value.equals(that.value) : that.value != null)
			return false;
		return propertyPath != null ? propertyPath.equals(that.propertyPath) : that.propertyPath != null;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getPropertyPath() {
		return propertyPath;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		int result = errorCode == null ? 0 : errorCode.hashCode();
		result = 31 * result + ((propertyPath == null) ? 0 : propertyPath.hashCode());
		result = 31 * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
