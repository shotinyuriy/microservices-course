package com.gridu.microservice.taxes.validation;

import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.validation.exception.CustomValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Another approach to implement the business validation
 */
@Service
public class StateValidatorService {

	@Autowired
	private StateDao stateDao;

	public void validateStateCodeExists(String stateCode) throws CustomValidationException {
		if (stateDao.findByCode(stateCode) == null) {
			ErrorResponse errorResponse = new ErrorResponse("error.state.notFound", stateCode);
			throw new CustomValidationException(Collections.singleton(errorResponse));
		}
	}
}
