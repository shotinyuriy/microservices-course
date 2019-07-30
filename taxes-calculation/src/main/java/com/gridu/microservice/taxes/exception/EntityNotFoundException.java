package com.gridu.microservice.taxes.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public EntityNotFoundException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	
	public EntityNotFoundException(String message,  HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
