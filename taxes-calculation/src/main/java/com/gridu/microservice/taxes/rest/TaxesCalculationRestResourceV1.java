package com.gridu.microservice.taxes.rest;

import java.util.List;

import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservice.taxes.entity.validator.StateEntityValidator;
import com.gridu.microservice.taxes.entity.validator.StateRuleEntityValidator;
import com.gridu.microservice.taxes.exception.EntityNotFoundException;
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
	private StateRuleEntityValidator stateRuleValidator; 
	
	@Autowired
	private StateEntityValidator stateEntityValidator;
	
	private StateEntityValidator getStateEntityValidator() {
		return stateEntityValidator;
	}
	
	private StateRuleEntityValidator getStateRuleValidator() {
		return stateRuleValidator;
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
	public StateRuleViewModel getStateRule(@PathVariable(value="stateCode") String stateCode) throws EntityNotFoundException {
		
		StateRule stateRule = getStateRuleService().getStateRule(stateCode);
		if(!getStateRuleValidator().isValid(stateRule)) {
			throw new EntityNotFoundException(stateCode, HttpStatus.NOT_FOUND);
		}

		return getStateRuleTransformer().toStateRuleViewModel(stateRule);
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
