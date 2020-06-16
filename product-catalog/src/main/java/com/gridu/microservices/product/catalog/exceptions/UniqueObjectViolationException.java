package com.gridu.microservices.product.catalog.exceptions;

public class UniqueObjectViolationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UniqueObjectViolationException(String message) {
		super(message);
	}
}
