package example.com.gridu.microservice.taxes.validation;

import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.exception.CustomConstraintViolationData;
import com.gridu.microservice.taxes.exception.CustomConstraintViolationException;
import com.gridu.microservice.taxes.model.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


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
			CustomConstraintViolationException exception = new CustomConstraintViolationException("Constraint violation occured:");
			exception.addViolationResult( new CustomConstraintViolationData("error.state.notFound", stateCode));
			throw exception;
		}
	}
}
