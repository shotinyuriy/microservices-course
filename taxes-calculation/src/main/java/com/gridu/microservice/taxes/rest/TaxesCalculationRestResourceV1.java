package com.gridu.microservice.taxes.rest;

import java.util.List;

import javax.validation.groups.Default;

import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.group.StateCodeValidationGroup;
import com.gridu.microservice.taxes.validation.group.TaxCategoryShouldExist;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservice.taxes.exception.handler.ErrorResponse;
import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.model.TaxRule;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel.StateRuleModel;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;
import com.gridu.microservice.taxes.rest.transformer.ValidationResultTransformer;

@RestController
@RequestMapping("/taxes")
public class TaxesCalculationRestResourceV1 {

	@Autowired
	private StateRuleService stateRuleService;

	@Autowired
	private StateRuleTransformer stateRuleTransformer;

	@Autowired
	private TaxCategoryService taxCategoryService;

	@Autowired
	private ValidationResultTransformer validationResultTransformer;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private StateService stateService;

	private StateService getStateService() {
		return stateService;
	}

	@PostMapping(value = "/stateRules/v1/{stateCode}", produces = "application/json")
	public ResponseEntity<?> addNewRule(@PathVariable(value = "stateCode") String stateCode,
			@RequestBody StateRulesRequestModel rules) {

		StateRule stateRule = provideStateRule(stateCode, rules);
		List<ValidationResult> validationResults = getValidationService().validate(stateRule, Default.class,
				StateCodeValidationGroup.class, TaxCategoryShouldExist.class);
		if (validationResults.isEmpty()) {
			StateRule saveStateRule = getStateRuleService().saveStateRule(stateRule);
			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
					.body("/stateRules/v1/" + saveStateRule.getState().getCode());
		} else {
			List<ErrorResponse> validationErrorResponse = getValidationResultTransformer()
					.provideValidationErrorResponse(validationResults);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResponse);
		}
	}

	@GetMapping(value = "/stateRules/v1/{stateCode}", produces = "application/json")
	public ResponseEntity<?> getStateRule(@PathVariable(value = "stateCode") String stateCode) {

		StateRule stateRule = getStateRuleService().getStateRule(stateCode);
		List<ValidationResult> validationResults = getValidationService().validate(stateRule, Default.class,
				StateCodeValidationGroup.class);
		if (!validationResults.isEmpty()) {
			List<ErrorResponse> responseList = getValidationResultTransformer()
					.provideValidationErrorResponse(validationResults);
			return ResponseEntity.badRequest().body(responseList);
		}
		return ResponseEntity.ok(getStateRuleTransformer().toStateRuleViewModel(stateRule));
	}

	@GetMapping(value = "/stateRules/v1", produces = "application/json")
	public ResponseEntity<List<StateRuleViewModel>> getStateRules() {
		return ResponseEntity.ok(getStateRuleTransformer().toStateRuleViewModel(getStateRuleService().getAll()));
	}

	@GetMapping(value = "/categories/v1", produces = "application/json")
	public ResponseEntity<List<TaxCategory>> getTaxCategories() {
		return ResponseEntity.ok().body(getTaxCategoryService().getAll());
	}

	public void setStateRuleTransformer(StateRuleTransformer stateRuleTransformer) {
		this.stateRuleTransformer = stateRuleTransformer;
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

	private ValidationResultTransformer getValidationResultTransformer() {
		return validationResultTransformer;
	}

	public void setValidationResultTransformer(ValidationResultTransformer validationResultTransfomer) {
		this.validationResultTransformer = validationResultTransfomer;
	}

	private ValidationService getValidationService() {
		return validationService;
	}

	private StateRule provideStateRule(String stateCode, StateRulesRequestModel rules) {
		StateRule stateRule = getStateRuleService().getStateRule(stateCode);
		if(stateRule == null || stateRule.getId() == null) {
			State state = getStateService().getStateDao().findByCode(stateCode);
			stateRule = new StateRule(state);
		}
		for (StateRuleModel stateRuleModel : rules.getRules()) {
			TaxCategory taxCategory = new TaxCategory(stateRuleModel.getCategory());
			stateRule.addTaxRule(new TaxRule(taxCategory, stateRuleModel.getTax()));
		}
		return stateRule;
	}

}
