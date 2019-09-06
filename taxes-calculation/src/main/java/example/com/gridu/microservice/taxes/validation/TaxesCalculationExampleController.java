package example.com.gridu.microservice.taxes.validation;

import java.util.List;
import java.util.Set;

import javax.validation.groups.Default;

import com.gridu.microservice.taxes.rest.transformer.ValidationResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservice.rest.validation.ErrorResponse;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.rest.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.group.StateCodeValidationGroup;

@RestController
@RequestMapping("/taxesExample")
public class TaxesCalculationExampleController {

	@Autowired
	private StateRuleService stateRuleService;

	@Autowired
	private StateRuleTransformer stateRuleTransformer;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private ValidationResultTransformer validationResultTransformer;


	/**
	 * @ExamplePurpose
	 */
	@GetMapping(value = "/stateRules/v1/validationFail", produces = "application/json")
	public ResponseEntity<?> validation() {
		/**
		 * hardcoded in order to get StateRule with invalid State object
		 *
		 * @see com.gridu.microservice.taxes.service.DataInitializeService#afterPropertiesSet()
		 */
		StateRule stateRule = getStateRuleService().getStateRule(5l);
		Set<ValidationResult> validationResults = getValidationService().validate(stateRule, Default.class,
			StateCodeValidationGroup.class);

		if (!validationResults.isEmpty()) {
			Set<ErrorResponse> responseList = getValidationResultTransformer().fromValidationResults(validationResults);
			return ResponseEntity.badRequest().body(responseList);
		}

		return ResponseEntity.ok(getStateRuleTransformer().toStateRuleViewModel(stateRule));
	}

	private StateRuleService getStateRuleService() {
		return stateRuleService;
	}

	private StateRuleTransformer getStateRuleTransformer() {
		return stateRuleTransformer;
	}

	private ValidationService getValidationService() {
		return validationService;
	}

	public ValidationResultTransformer getValidationResultTransformer() {
		return validationResultTransformer;
	}

	public void setValidationResultTransformer(ValidationResultTransformer validationResultTransformer) {
		this.validationResultTransformer = validationResultTransformer;
	}
}
