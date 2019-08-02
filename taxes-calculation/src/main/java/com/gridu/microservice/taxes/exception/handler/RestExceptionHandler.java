package com.gridu.microservice.taxes.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gridu.microservice.taxes.exception.EntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
		return provideResponseEntity(new ErrorResponse(exception.getStatus().toString(), exception.getMessage()), exception.getStatus());
	}
	
	private ResponseEntity<Object> provideResponseEntity(ErrorResponse entityError, HttpStatus status) {
		List<ErrorResponse> responseList = new ArrayList<ErrorResponse>();
		responseList.add(entityError);
		return new ResponseEntity<>(responseList, status);
	}
 }