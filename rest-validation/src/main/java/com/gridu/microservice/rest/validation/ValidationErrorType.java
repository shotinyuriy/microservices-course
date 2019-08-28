package com.gridu.microservice.rest.validation;

public interface ValidationErrorType {
	String ERROR = "error";

	String MISSING = "missing";
	String NOT_FOUND = "notFound";
	String INVALID = "invalid";
	String EMPTY = "empty";
	String TOO_SMALL = "tooSmall";
	String TOO_BIG = "tooBig";
}
