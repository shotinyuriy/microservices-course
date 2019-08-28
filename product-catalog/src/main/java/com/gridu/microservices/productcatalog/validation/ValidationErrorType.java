package com.gridu.microservices.productcatalog.validation;

public interface ValidationErrorType {
	String ERROR = "error";
	String NOT_FOUND = "notFound";
	String INVALID = "invalid";
	String EMPTY = "empty";
	String EXISTS = "alreadyExists";
}
