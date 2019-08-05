package com.gridu.microservice.taxes.validation;

import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.exception.ConstraintViolation;
import com.gridu.microservice.taxes.exception.CustomConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;


/**
 * @ExamplePurpose
 * Another approach to implement the business validation
 */
@Service
public class StateValidatorService {

	@Autowired
	private StateDao stateDao;

	public void validateStateCodeExists(String stateCode) {
		if (stateDao.findByCode(stateCode) == null) {
			ConstraintViolation violation = new ConstraintViolation("error.state.notFound", stateCode);
			throw new CustomConstraintViolationException(Collections.singleton(violation));
		}
	}
}
