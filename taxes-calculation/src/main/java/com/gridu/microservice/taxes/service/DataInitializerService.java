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

		saveIfNotExists(new TaxCategory("books"));
		saveIfNotExists(new TaxCategory("clothing"));
		saveIfNotExists(new TaxCategory("electronic devices"));


		saveIfNotExists(new State("AZ", "Arizona"));
		saveIfNotExists(new State("CA", "California"));
		saveIfNotExists(new State("NJ", "New Jersey"));
		saveIfNotExists(new State("NY", "New York"));
		saveIfNotExists(new State("PA", "Pennsylvania"));

		getStateService().getAll().forEach(s -> {
			removeStateRuleIfExists(s.getCode());
		});

		StateRule stateRuleAz = new StateRule(getStateService().findByCode("AZ"));
		stateRuleAz.addTaxRule(new TaxRule(getTaxCategoryService().findById(1l), 0.12));
		stateRuleAz.addTaxRule(new TaxRule(getTaxCategoryService().findById(2l), 0.22));
		saveIfNotExists(stateRuleAz);
		
		
		StateRule stateRulePa = new StateRule(getStateService().findByCode("PA"));
		stateRulePa.addTaxRule(new TaxRule(getTaxCategoryService().findById(2l), 0.08));
		stateRulePa.addTaxRule(new TaxRule(getTaxCategoryService().findById(3l), 0.15));
		saveIfNotExists(stateRulePa);


		
		//BEGIN OF @ExamplePurpose
//		StateRule emptyStateRule = new StateRule();
//		emptyStateRule.setId(5l);
//		emptyStateRule.setState(new State());
//		emptyStateRule.setTaxRules(new ArrayList<TaxRule>());
//		getStateRuleService().saveStateRule(emptyStateRule);
		//END OF @ExamplePurpose
	}

	protected void saveIfNotExists(TaxCategory taxCategory) {
		if (getTaxCategoryService().findByCategory(taxCategory.getName()) == null) {
			getTaxCategoryService().saveTaxCategory(taxCategory);
		}
	}

	protected void saveIfNotExists(State state) {
		if (getStateService().findByCode(state.getCode()) == null) {
			getStateService().saveState(state);
		}
	}

	protected void saveIfNotExists(StateRule stateRule) {
		if(stateRule.getState().getCode() != null) {
			if (getStateRuleService().getStateRule(stateRule.getState().getCode()) == null) {
				getStateRuleService().saveStateRule(stateRule);
			}
		}
	}

	protected void removeStateRuleIfExists(String stateCode) {
		StateRule stateRule = getStateRuleService().getStateRule(stateCode);
		if (stateRule != null) {
			getStateRuleService().removeSateRule(stateRule);
		}
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
