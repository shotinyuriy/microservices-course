package com.gridu.microservice.taxes.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.validation.groups.Default;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.model.TaxRule;
import com.gridu.microservice.taxes.rest.TaxesCalculationRestResourceV1;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TaxesCalculationRestResourceV1Test {

	private static String STATE_CODE_AZ = "az";
	private static String STATE_NAME_ARIZONA = "ARIZONA";
	private static String TAX_CATEGORY_DEVICES = "computer devices";
	private static Double TAX_1 = 0.12;

	@InjectMocks
	private TaxesCalculationRestResourceV1 controller;

	@Mock
	private StateRuleService stateRuleServiceMock;

	@Mock
	private ValidationService validationServiceMock;

	@Before
	public void setBefore() {
		controller.setStateRuleTransformer(new StateRuleTransformer());
	}

	@Test
	public void getStateRule_byValidStateCode() {

		// ARRANGE
		StateRule stateRule = new StateRule(new State(STATE_CODE_AZ, STATE_NAME_ARIZONA));
		TaxCategory taxCategory = new TaxCategory(TAX_CATEGORY_DEVICES);
		stateRule.addTaxRule(new TaxRule(taxCategory, TAX_1));

		Mockito.when(stateRuleServiceMock.getStateRule(STATE_CODE_AZ)).thenReturn(stateRule);
		
		Mockito.when(validationServiceMock.validate(stateRule, Default.class))
				.thenReturn(new ArrayList<ValidationResult>());
		// ACT
		ResponseEntity<?> stateRuleResponseEntity = controller.getStateRule(STATE_CODE_AZ);
		
		// ASSERT
		Object responseBody = stateRuleResponseEntity.getBody();
		assertTrue(responseBody instanceof StateRuleViewModel);
		StateRuleViewModel viewModel = (StateRuleViewModel) responseBody;
		assertEquals(STATE_CODE_AZ, viewModel.getState());
		assertEquals(TAX_1, viewModel.getRules().get(0).get(TAX_CATEGORY_DEVICES));
		Mockito.verify(stateRuleServiceMock, Mockito.times(2)).getStateRule(Mockito.anyString());
		Mockito.verify(stateRuleServiceMock, Mockito.never()).getAll();
	}

	@Test
	public void getStateRule_byInvalidStateCode() {
		
	}
	@Test
	public void addNewRule_existingStateRule() {
		
	}
	
	@Test
	public void addNewRule_unexistingStateRule() {
		
	}
	
	@Test
	public void addNewRule_invalidData() {
		
	}
}
