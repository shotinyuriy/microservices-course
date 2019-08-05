package com.gridu.microservice.taxes.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.groups.Default;

import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.group.StateCodeValidationGroup;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservice.taxes.exception.handler.ErrorResponse;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;

@RestController
@RequestMapping("/taxes")
public class TaxesCalculationRestResourceV1 {

	@Autowired
	private StateRuleService stateRuleService;

	@Autowired
	private StateRuleTransformer stateRuleTransformer;

	@Autowired
	private StateService stateService;

	@Autowired
	private TaxCategoryService taxCategoryService;

	@Autowired
	private ValidationService validationService;

	@GetMapping(value = "/stateRules/v1/{stateCode}", produces = "application/json")
	public ResponseEntity<Object> getStateRule(@PathVariable(value = "stateCode") String stateCode) {

		StateRule stateRule = getStateRuleService().getStateRule(stateCode);
		if (stateRule == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			List<ValidationResult> validationResults = getValidationService().validate(stateRule, Default.class,
					StateCodeValidationGroup.class);
			if (!validationResults.isEmpty()) {
				List<ErrorResponse> responseList = provideValidationErrorResponse(validationResults);
				return ResponseEntity.badRequest().body(responseList);
			}
		}
		return ResponseEntity
				.ok(getStateRuleTransformer().toStateRuleViewModel(getStateRuleService().getStateRule(stateCode)));
	}

	@GetMapping(value = "/stateRules/v1", produces = "application/json")
	public List<StateRuleViewModel> getStateRules() {
		return getStateRuleTransformer().toStateRuleViewModel(getStateRuleService().getAll());

	}

	@PostMapping(value = "/stateRules/v1/{stateCode}", produces = "application/json")
	public ResponseEntity<Object> addNewRule(@PathVariable(value = "stateCode") String stateCode,
			@RequestBody StateRulesRequestModel rules) {
		// to do
		return ResponseEntity.ok("Response for post state rules");
	}

	public StateService getStateService() {
		return stateService;
	}

	@GetMapping(value = "/categories/v1", produces = "application/json")
	public List<TaxCategory> getTaxCategories() {
		return getTaxCategoryService().getAll();
	}

	public void setStateRuleService(StateRuleService stateRuleService) {
		this.stateRuleService = stateRuleService;
	}

	public void setTaxCategoryService(TaxCategoryService taxCategoryService) {
		this.taxCategoryService = taxCategoryService;
	}

	/**
	 * @ExamplePurpose
	 */
	@GetMapping(value = "/stateRules/v1/validationFail", produces = "application/json")
	public ResponseEntity<Object> validation() {
		/**
		 * hardcoded in order to get StateRule with invalid State object
		 * 
		 * @see com.gridu.microservice.taxes.service.DataInitializeService#afterPropertiesSet()
		 */
		StateRule stateRule = getStateRuleService().getStateRule(5l);
		List<ValidationResult> validationResults = getValidationService().validate(stateRule, Default.class,
				StateCodeValidationGroup.class);

		if (!validationResults.isEmpty()) {
			List<ErrorResponse> responseList = new ArrayList<ErrorResponse>();
			for (ValidationResult validationResult : validationResults) {
				ErrorResponse errorResponse = new ErrorResponse(validationResult.getErrorCode(),
						validationResult.getValue());
				responseList.add(errorResponse);
			}
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

	private TaxCategoryService getTaxCategoryService() {
		return taxCategoryService;
	}

	private ValidationService getValidationService() {
		return validationService;
	}

	private List<ErrorResponse> provideValidationErrorResponse(List<ValidationResult> validationResults) {
		List<ErrorResponse> responseList = new ArrayList<ErrorResponse>();
		for (ValidationResult validationResult : validationResults) {
			ErrorResponse errorResponse = new ErrorResponse(validationResult.getErrorCode(),
					validationResult.getValue());
			responseList.add(errorResponse);
		}
		return responseList;
	}
}
