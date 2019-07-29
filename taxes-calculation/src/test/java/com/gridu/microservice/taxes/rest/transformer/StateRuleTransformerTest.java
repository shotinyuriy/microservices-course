package com.gridu.microservice.taxes.rest.transformer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.model.TaxRule;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

public class StateRuleTransformerTest {

	
	private String techTaxCategory = "tech";
	private String accessoriesTaxCategory = "asccessories";
	private Double tax1 = 0.12;
	private Double tax2 = 0.32;
	private String caStateCode = "CA";
	private String californiaStateName = "California";
	private String azStateCode = "AZ";
	private String arizonaStateName = "Arizona";
	private Long id1 = 23l;
	private Long id2 = 123456789l;
	
	@Test
	public void testToStateRuleViewModelListOfStateRule() {
		StateRuleTransformer transformer = new StateRuleTransformer();
		List<StateRuleViewModel> viewModel = transformer.toStateRuleViewModel(createStateRules());
		
		assertTrue(viewModel != null);
		assertEquals(2, viewModel.size());
		
		StateRuleViewModel azViewModel = viewModel.get(0);
		assertEquals(id1, azViewModel.getId());
		assertEquals(azStateCode, azViewModel.getState());
		assertEquals(tax1, azViewModel.getRules().get(0).get(techTaxCategory));
		assertEquals(tax2, azViewModel.getRules().get(0).get(accessoriesTaxCategory));
		
		StateRuleViewModel caViewModel = viewModel.get(1);
		assertEquals(id2, caViewModel.getId());
		assertEquals(caStateCode, caViewModel.getState());
		assertEquals(tax1, caViewModel.getRules().get(0).get(accessoriesTaxCategory));
		assertEquals(tax2, caViewModel.getRules().get(0).get(techTaxCategory));
	}

	@Test
	public void testToStateRuleViewModelStateRule() {
		StateRuleTransformer transformer = new StateRuleTransformer();
		StateRuleViewModel viewModel = transformer.toStateRuleViewModel(createArizonaStateRule());
		
		assertTrue(viewModel != null);
		assertEquals(id1, viewModel.getId());
		assertEquals(azStateCode, viewModel.getState());
		assertEquals(tax1, viewModel.getRules().get(0).get(techTaxCategory));
		assertEquals(tax2, viewModel.getRules().get(0).get(accessoriesTaxCategory));
	}
	
	private StateRule createArizonaStateRule() {
		
		StateRule stateRule = new StateRule();
		stateRule.setId(id1);
		State state = new State(azStateCode, arizonaStateName);
		stateRule.setState(state);
		stateRule.addTaxRule(new TaxRule(new TaxCategory(techTaxCategory), tax1));
		stateRule.addTaxRule(new TaxRule(new TaxCategory(accessoriesTaxCategory), tax2));
			
		return stateRule;
	}
	
	private StateRule createCaliforniaStateRule() {
		StateRule stateRule = new StateRule();
		stateRule.setId(id2);
		State state = new State(caStateCode, californiaStateName);
		stateRule.setState(state);
		stateRule.addTaxRule(new TaxRule(new TaxCategory(techTaxCategory), tax2));
		stateRule.addTaxRule(new TaxRule(new TaxCategory(accessoriesTaxCategory), tax1));
			
		return stateRule;

	}
	
	private List<StateRule> createStateRules() {
		List<StateRule> stateRules = new ArrayList<StateRule>();
		stateRules.add(createArizonaStateRule());
		stateRules.add(createCaliforniaStateRule());
		return stateRules;
	}
}
