package com.gridu.microservice.taxes.validation;

import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.exception.ConstraintViolation;
import com.gridu.microservice.taxes.exception.CustomConstraintViolationException;
import com.gridu.microservice.taxes.model.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
		State state = stateDao.findByCode(stateCode);
		if (state == null || StringUtils.isEmpty(state.getCode())) {
			ConstraintViolation violation = new ConstraintViolation("error.state.notFound", stateCode);
			throw new CustomConstraintViolationException(Collections.singleton(violation));
		}
	}
}
