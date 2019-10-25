package com.gridu.microservice.taxes.rest;

import com.gridu.microservice.rest.validation.ValidationResult;
import com.gridu.microservice.taxes.exception.CustomConstraintViolationException;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.model.TaxRule;
import com.gridu.microservice.taxes.model.TaxesCalculationItemsModel;
import com.gridu.microservice.taxes.model.TaxesCalculationItemsModel.TaxCalculationItemModel;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel.StateRuleModel;
import com.gridu.microservice.taxes.rest.model.TaxesCalculationResponse;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;
import com.gridu.microservice.taxes.rest.transformer.ValidationResultTransformer;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCalculatorService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.group.StateCodeValidationGroup;
import com.gridu.microservice.taxes.validation.group.TaxCategoryShouldExist;
import com.gridu.microservice.taxes.view.StateRuleViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/taxes")
public class TaxesCalculationRestResourceV2 {

	Logger LOGGER = LoggerFactory.getLogger(TaxesCalculationRestResourceV2.class);

	@Autowired
	private StateRuleService stateRuleService;

	@Autowired
	private StateRuleTransformer stateRuleTransformer;

	@Autowired
	private StateService stateService;

	@Autowired
	private TaxCalculatorService taxCalculatorService;

	@Autowired
	private TaxCategoryService taxCategoryService;

	@Autowired
	private ValidationResultTransformer validationResultTransformer;

	@Autowired
	private ValidationService validationService;

	@PostMapping(value = "/calculation/v2", produces = "application/json")
	public ResponseEntity<TaxesCalculationResponse> calculateTaxesPerItem(@RequestBody TaxesCalculationItemsModel order) {

		LOGGER.info("Order=" + order);

		TaxesCalculationResponse response = new TaxesCalculationResponse();
		Set<ValidationResult> modelValidationResult = getValidationService().validate(order);
		if (!modelValidationResult.isEmpty()) {
			response.setErrors(validationResultTransformer.fromValidationResults(modelValidationResult));
			return ResponseEntity.badRequest().body(response);
		}

		Set<ValidationResult> validationResults = validateTaxesCalculationData(order);
		if (!validationResults.isEmpty()) {
			response.setErrors(validationResultTransformer.fromValidationResults(validationResults));
			return ResponseEntity.badRequest().body(response);
		}

		getTaxCalculatorService().updateTaxCalcItemsModelWithTax(order);

		response.setOrder(order);
		return ResponseEntity.ok().body(response);
	}

	public void setStateRuleTransformer(StateRuleTransformer stateRuleTransformer) {
		this.stateRuleTransformer = stateRuleTransformer;
	}

	public void setTaxCalculatorService(TaxCalculatorService taxCalculatorService) {
		this.taxCalculatorService = taxCalculatorService;
	}
	
	public void setValidationResultTransformer(ValidationResultTransformer validationResultTransfomer) {
		this.validationResultTransformer = validationResultTransfomer;
	}

	private StateRuleService getStateRuleService() {
		return stateRuleService;
	}

	private StateRuleTransformer getStateRuleTransformer() {
		return stateRuleTransformer;
	}

	private StateService getStateService() {
		return stateService;
	}

	private TaxCalculatorService getTaxCalculatorService() {
		return taxCalculatorService;
	}

	private TaxCategoryService getTaxCategoryService() {
		return taxCategoryService;
	}

	private ValidationResultTransformer getValidationResultTransformer() {
		return validationResultTransformer;
	}

	private ValidationService getValidationService() {
		return validationService;
	}

	private StateRule provideStateRule(String stateCode, StateRulesRequestModel rules) {
		StateRule stateRule = getStateRuleService().getStateRule(stateCode);
		if (stateRule == null || stateRule.getId() == null) {
			stateRule = new StateRule(getStateService().getStateDao().findByCode(stateCode));
		}
		for (StateRuleModel stateRuleModel : rules.getRules()) {
			TaxCategory category = getTaxCategoryService().findByCategory(stateRuleModel.getCategory());
			stateRule.addTaxRule(new TaxRule(category, stateRuleModel.getTax()));
		}
		return stateRule;
	}

	private Set<ValidationResult> validateTaxesCalculationData(TaxesCalculationItemsModel order) {
		Set<ValidationResult> validationResults = new HashSet<ValidationResult>();
		StateRule stateRule = getStateRuleService().getStateRule(order.getStateCode());
		validationResults
				.addAll(getValidationService().validate(stateRule, Default.class, StateCodeValidationGroup.class));

		for (TaxCalculationItemModel taxCalcItem : order.getItems()) {
			TaxCategory taxCategory = getTaxCategoryService().findByCategory(taxCalcItem.getCategory());
			validationResults
					.addAll(getValidationService().validate(taxCategory, Default.class, TaxCategoryShouldExist.class));
		}
		return validationResults;
	}

}
