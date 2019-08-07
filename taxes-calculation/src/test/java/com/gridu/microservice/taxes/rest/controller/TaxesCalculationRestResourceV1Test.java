package com.gridu.microservice.taxes.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.validation.groups.Default;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.exception.handler.ErrorResponse;
import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.model.TaxRule;
import com.gridu.microservice.taxes.rest.TaxesCalculationRestResourceV1;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel.StateRuleModel;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;
import com.gridu.microservice.taxes.rest.transformer.ValidationResultTransformer;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.group.StateCodeValidationGroup;
import com.gridu.microservice.taxes.validation.group.TaxCategoryShouldExist;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TaxesCalculationRestResourceV1Test {

	private static String STATE_CODE_AZ = "az";
	private static String STATE_NAME_ARIZONA = "ARIZONA";
	private static Double TAX_1 = 0.12;
	private static Double TAX_2 = 0.15;
	private static String TAX_CATEGORY_CLOTHES = "clothes";
	private static String TAX_CATEGORY_DEVICES = "computer devices";

	@InjectMocks
	private TaxesCalculationRestResourceV1 controller;
	
	@Mock
	private StateService stateServiceMock;
	
	@Mock
	private TaxCategoryService taxCategoryServiceMock;

	@Mock
	private StateRuleService stateRuleServiceMock;

	@Mock
	private ValidationService validationServiceMock;

	
	@Before
	public void setBefore() {
		controller.setStateRuleTransformer(new StateRuleTransformer());
		controller.setValidationResultTransformer(new ValidationResultTransformer());
	}
	
	@Test
	public void addNewRule_sucessfulAdding() {
		// ARRANGE
		StateRulesRequestModel rulesModel = new StateRulesRequestModel();
		List<StateRuleModel> rules = new ArrayList<StateRulesRequestModel.StateRuleModel>();
		rules.add(new StateRuleModel(TAX_CATEGORY_DEVICES, TAX_1));
		rules.add(new StateRuleModel(TAX_CATEGORY_CLOTHES, TAX_2));
		rulesModel.setRules(rules);

		StateDao stateDao = Mockito.mock(StateDao.class);
		Mockito.when(stateDao.findByCode(STATE_CODE_AZ)).thenReturn( new State(STATE_CODE_AZ, STATE_NAME_ARIZONA));
		Mockito.when(stateServiceMock.getStateDao()).thenReturn(stateDao);
		
		Mockito.when(taxCategoryServiceMock.findByCategory(TAX_CATEGORY_DEVICES)).thenReturn(new TaxCategory(TAX_CATEGORY_DEVICES));
		Mockito.when(taxCategoryServiceMock.findByCategory(TAX_CATEGORY_CLOTHES)).thenReturn(new TaxCategory(TAX_CATEGORY_CLOTHES));
		
		ArgumentCaptor<StateRule> stateRuleCaptor = ArgumentCaptor.forClass(StateRule.class);
		Mockito.when(validationServiceMock.validate(stateRuleCaptor.capture(), Matchers.eq(Default.class),
				Matchers.eq(StateCodeValidationGroup.class), Matchers.eq(TaxCategoryShouldExist.class)))
				.thenReturn(new ArrayList<ValidationResult>());
		Mockito.when(stateRuleServiceMock.saveStateRule(stateRuleCaptor.capture())).thenAnswer(new Answer<StateRule>() {
			@Override
			public StateRule answer(InvocationOnMock invocation) throws Throwable {
				return (StateRule) invocation.getArguments()[0];
			}
		});

		// ACT
		ResponseEntity<?> response = controller.addNewRule(STATE_CODE_AZ, rulesModel);

		// ASSERT
		Mockito.verify(stateRuleServiceMock, Mockito.times(1)).saveStateRule(stateRuleCaptor.getValue());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("/stateRules/v1/" + STATE_CODE_AZ, response.getBody().toString());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
	}

	@Test
	public void addNewRule_unsucessfulAdding() {
		// ARRANGE
		StateRulesRequestModel rulesModel = new StateRulesRequestModel();
		List<StateRuleModel> rules = new ArrayList<StateRulesRequestModel.StateRuleModel>();
		rules.add(new StateRuleModel(TAX_CATEGORY_DEVICES, TAX_1));
		rules.add(new StateRuleModel(TAX_CATEGORY_CLOTHES, TAX_2));
		rulesModel.setRules(rules);

		StateDao stateDao = Mockito.mock(StateDao.class);
		Mockito.when(stateDao.findByCode(STATE_CODE_AZ)).thenReturn( new State(STATE_CODE_AZ, STATE_NAME_ARIZONA));
		Mockito.when(stateServiceMock.getStateDao()).thenReturn(stateDao);
		
		Mockito.when(taxCategoryServiceMock.findByCategory(TAX_CATEGORY_DEVICES)).thenReturn(new TaxCategory());
		Mockito.when(taxCategoryServiceMock.findByCategory(TAX_CATEGORY_CLOTHES)).thenReturn(new TaxCategory());

		ArgumentCaptor<StateRule> stateRuleCaptor = ArgumentCaptor.forClass(StateRule.class);
		List<ValidationResult> result = new ArrayList<ValidationResult>();
		String invalidCode = "invalid code";
		String errorCode = "error.invalid.state.code";
		result.add(new ValidationResult(errorCode, invalidCode));
		
		Mockito.when(validationServiceMock.validate(stateRuleCaptor.capture(), Matchers.eq(Default.class),
				Matchers.eq(StateCodeValidationGroup.class), Matchers.eq(TaxCategoryShouldExist.class)))
				.thenReturn(result);

		// ACT
		ResponseEntity<?> response = controller.addNewRule(STATE_CODE_AZ, rulesModel);

		// ASSERT
		Mockito.verify(stateRuleServiceMock, Mockito.never()).saveStateRule(stateRuleCaptor.getValue());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		@SuppressWarnings("unchecked")
		List<ErrorResponse> errorResponses = (List<ErrorResponse>) response.getBody();
		assertEquals(1, errorResponses.size());
		assertEquals(errorCode, errorResponses.get(0).getErrorCode());
		assertEquals(invalidCode, errorResponses.get(0).getValue());
	}

	@Test
	public void getStateRule_byInvalidStateCode() {
		// ARRANGE
		StateRule stateRule = new StateRule();
		Mockito.when(stateRuleServiceMock.getStateRule(Mockito.anyString())).thenReturn(stateRule);

		List<ValidationResult> validationResult = new ArrayList<ValidationResult>();
		String invalidCode = "invalid code";
		String errorCode = "error.invalid.state.code";
		validationResult.add(new ValidationResult(errorCode, invalidCode));

		Mockito.when(validationServiceMock.validate(stateRule, Default.class, StateCodeValidationGroup.class))
				.thenReturn(validationResult);
		// ACT
		ResponseEntity<?> stateRuleResponseEntity = controller.getStateRule(invalidCode);

		// ASSERT
		Object responseBody = stateRuleResponseEntity.getBody();
		assertTrue(responseBody instanceof List<?>);

		@SuppressWarnings("unchecked")
		List<ErrorResponse> errorResponses = (List<ErrorResponse>) responseBody;
		assertEquals(1, errorResponses.size());
		assertEquals(errorCode, errorResponses.get(0).getErrorCode());
		assertEquals(invalidCode, errorResponses.get(0).getValue());
	}

	@Test
	public void getStateRule_byValidStateCode() {

		// ARRANGE
		StateRule stateRule = new StateRule(new State(STATE_CODE_AZ, STATE_NAME_ARIZONA));
		TaxCategory taxCategory = new TaxCategory(TAX_CATEGORY_DEVICES);
		stateRule.addTaxRule(new TaxRule(taxCategory, TAX_1));

		Mockito.when(stateRuleServiceMock.getStateRule(STATE_CODE_AZ)).thenReturn(stateRule);
		Mockito.when(validationServiceMock.validate(stateRule, Default.class, StateCodeValidationGroup.class))
				.thenReturn(new ArrayList<ValidationResult>());
		// ACT
		ResponseEntity<?> stateRuleResponseEntity = controller.getStateRule(STATE_CODE_AZ);

		// ASSERT
		Object responseBody = stateRuleResponseEntity.getBody();
		assertTrue(responseBody instanceof StateRuleViewModel);
		StateRuleViewModel viewModel = (StateRuleViewModel) responseBody;
		assertEquals(STATE_CODE_AZ, viewModel.getState());
		assertEquals(TAX_1, viewModel.getRules().get(0).get(TAX_CATEGORY_DEVICES));
		Mockito.verify(stateRuleServiceMock, Mockito.times(1)).getStateRule(Mockito.anyString());
		Mockito.verify(stateRuleServiceMock, Mockito.never()).getAll();
	}
}
