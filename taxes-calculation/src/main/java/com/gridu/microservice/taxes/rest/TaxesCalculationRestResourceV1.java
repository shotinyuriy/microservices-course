package com.gridu.microservice.taxes.rest;

import java.util.List;

import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public StateRuleViewModel getStateRule(@PathVariable(value="stateCode") String stateCode) {
		return getStateRuleTransformer().toStateRuleViewModel(getStateRuleService().getStateRule(stateCode));
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
