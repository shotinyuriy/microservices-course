package com.gridu.microservice.taxes.service;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.model.TaxCategory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInitializerService implements InitializingBean {

	@Autowired
	private TaxCategoryService taxCategoryService;

	@Autowired
	private StateService stateService;

	@Override
	public void afterPropertiesSet() throws Exception {
		getTaxCategoryService().saveTaxCategory(new TaxCategory("books"));
		getTaxCategoryService().saveTaxCategory(new TaxCategory("clothing"));
		getTaxCategoryService().saveTaxCategory(new TaxCategory("electronic devices"));


		getStateService().saveState(new State("AZ", "Arizona"));
		getStateService().saveState(new State("CA", "California"));
		getStateService().saveState(new State("NJ", "New Jersey"));
		getStateService().saveState(new State("NY", "New York"));
		getStateService().saveState(new State("PA", "Pennsylvania"));
	}

	public TaxCategoryService getTaxCategoryService() {
		return taxCategoryService;
	}

	public void setTaxCategoryService(TaxCategoryService taxCategoryService) {
		this.taxCategoryService = taxCategoryService;
	}

	public StateService getStateService() {
		return stateService;
	}

	public void setStateService(StateService stateService) {
		this.stateService = stateService;
	}
}
