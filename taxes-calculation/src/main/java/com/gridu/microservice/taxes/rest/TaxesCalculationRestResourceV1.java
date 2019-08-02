package com.gridu.microservice.taxes.rest;

import java.util.List;

import javax.validation.groups.Default;

import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservice.taxes.exception.ConstraintViolationException;
import com.gridu.microservice.taxes.exception.handler.ErrorResponseConstants;
import com.gridu.microservice.taxes.exception.ConstraintViolation;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;

@RestController
@RequestMapping("/taxes")
public class TaxesCalculationRestResourceV1 {

	@Autowired
	private TaxCategoryService taxCategoryService;

	@Autowired
	private StateRuleService stateRuleService;

	@Autowired
	private StateRuleTransformer stateRuleTransformer;

	@Autowired
	private ValidationService validationService;

	private ValidationService getValidationService() {
		return validationService;
	}

	private StateRuleTransformer getStateRuleTransformer() {
		return stateRuleTransformer;
	}

	@GetMapping(value = "/categories/v1", produces = "application/json")
	public List<TaxCategory> getTaxCategories() {
		return getTaxCategoryService().getAll();
	}

	@GetMapping(value = "/stateRules/v1", produces = "application/json")
	public List<StateRuleViewModel> getStateRules() {
		return getStateRuleTransformer().toStateRuleViewModel(getStateRuleService().getAll());
	}

	@GetMapping(value = "/stateRules/v1/{stateCode}", produces = "application/json")
	public StateRuleViewModel getStateRule(@PathVariable(value = "stateCode") String stateCode) {

		StateRule stateRule = getStateRuleService().getStateRule(stateCode);
		if (stateRule == null) {
			ValidationResult validationResult = new ValidationResult(
					StateRule.ERROR_CODE_PATH + ErrorResponseConstants.NULL, stateCode);
			throw provideConstraintValidationException(validationResult);
		} else {
			List<ValidationResult> validationResults = getValidationService().validate(stateRule, Default.class);
			if (!validationResults.isEmpty()) {
				throw provideConstraintValidationException(validationResults.stream().toArray(ValidationResult[]::new));
			}
		}
		return getStateRuleTransformer().toStateRuleViewModel(stateRule);
	}

	private ConstraintViolationException provideConstraintValidationException(ValidationResult... validationResults) {
		ConstraintViolationException exception = new ConstraintViolationException("State rule not valid.");
		for (ValidationResult result : validationResults) {
			exception.addValidationResult(createViolationByResult(result));
		}
		return exception;
	}

	private ConstraintViolation createViolationByResult(ValidationResult result) {
		ConstraintViolation constraintViolation = new ConstraintViolation();
		constraintViolation.setErrorMessage(result.getErrorCode());
		constraintViolation.setValue(result.getValue());
		return constraintViolation;
	}

	private StateRuleService getStateRuleService() {
		return stateRuleService;
	}

	private TaxCategoryService getTaxCategoryService() {
		return taxCategoryService;
	}

	public void setTaxCategoryService(TaxCategoryService taxCategoryService) {
		this.taxCategoryService = taxCategoryService;
	}

	public void setStateRuleService(StateRuleService stateRuleService) {
		this.stateRuleService = stateRuleService;
	}
}
