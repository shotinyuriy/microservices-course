package com.gridu.microservice.taxes.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gridu.microservice.taxes.exception.CustomConstraintViolationData;
import com.gridu.microservice.taxes.exception.CustomConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomConstraintViolationException.class)
	protected ResponseEntity<Object> handleEntityNotFoundException(CustomConstraintViolationException exception) {

		if (exception.getViolationResults().isEmpty()) {
			return provideResponseEntity(new ErrorResponse(HttpStatus.NOT_FOUND.toString(), exception.getMessage()),
					HttpStatus.NOT_FOUND);
		} else {
			List<ErrorResponse> responseList = new ArrayList<ErrorResponse>();
			for (CustomConstraintViolationData result : exception.getViolationResults())
				responseList.add(new ErrorResponse(result.getMessage(), result.getValue()));
			return new ResponseEntity<>(responseList, HttpStatus.NOT_FOUND);
		}
	}

	private ResponseEntity<Object> provideResponseEntity(ErrorResponse entityError, HttpStatus status) {
		List<ErrorResponse> responseList = new ArrayList<ErrorResponse>();
		responseList.add(entityError);
		return new ResponseEntity<>(responseList, status);
	}
}
