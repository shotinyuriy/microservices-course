package com.gridu.microservice.taxes.service;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.model.TaxRule;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 
 * Creates custom application data
 *
 */
@Service
public class DataInitializerService implements InitializingBean {

	@Autowired
	private TaxCategoryService taxCategoryService;

	@Autowired
	private StateService stateService;

	@Autowired
	private StateRuleService stateRuleService;

	public StateRuleService getStateRuleService() {
		return stateRuleService;
	}

	// executed after all beans created and dependencies are injected
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
		
		StateRule stateRuleAz = new StateRule(getStateService().findByCode("AZ"));
		stateRuleAz.addTaxRule(new TaxRule(getTaxCategoryService().findById(1l), 0.12));
		stateRuleAz.addTaxRule(new TaxRule(getTaxCategoryService().findById(2l), 0.22));
		getStateRuleService().saveStateRule(stateRuleAz);
		
		
		StateRule stateRulePa = new StateRule(getStateService().findByCode("PA"));
		stateRulePa.addTaxRule(new TaxRule(getTaxCategoryService().findById(2l), 0.08));
		stateRulePa.addTaxRule(new TaxRule(getTaxCategoryService().findById(3l), 0.15));
		getStateRuleService().saveStateRule(stateRulePa);
		
		//BEGIN OF @ExamplePurpose
//		StateRule emptyStateRule = new StateRule();
//		emptyStateRule.setId(5l);
//		emptyStateRule.setState(new State());
//		emptyStateRule.setTaxRules(new ArrayList<TaxRule>());
//		getStateRuleService().saveStateRule(emptyStateRule);
		//END OF @ExamplePurpose
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
