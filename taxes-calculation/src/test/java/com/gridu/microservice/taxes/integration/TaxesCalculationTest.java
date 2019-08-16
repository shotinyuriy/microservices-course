package com.gridu.microservice.taxes.integration;

import static org.junit.Assert.*;

import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import com.gridu.microservice.taxes.exception.handler.RestExceptionHandler;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.rest.TaxesCalculationRestResourceV1;
import com.gridu.microservice.taxes.rest.model.PostStateRuleRequest;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel;
import com.gridu.microservice.taxes.rest.model.StateRulesRequestModel.StateRuleModel;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;
import com.gridu.microservice.taxes.rest.transformer.ValidationResultTransformer;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;
import com.gridu.microservice.taxes.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.group.TaxCategoryShouldExist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gridu.microservice.taxes.service.DataInitializerService;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;

import javax.validation.groups.Default;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;

@ActiveProfiles("entity-manager")
@ContextConfiguration("file:src/main/webapp/config/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaxesCalculationTest {

	@Autowired
	private DataInitializerService dataInitializerService;

	@Autowired
	private TaxCategoryService taxCategoryService;

	@Autowired
	private StateService stateService;

	@Autowired
	private StateRuleService stateRuleService;

	@Autowired
	private TaxCategoryDao taxCategoryDao;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private ValidationResultTransformer validationResultTransformer;

	@Autowired
	private StateRuleTransformer stateRuleTransformer;

	@Autowired
	private TaxesCalculationRestResourceV1 controller;

	@Autowired
	private RestExceptionHandler exceptionHandler;
	
	@Before
	public void setUp() {
		// ARRANGE + ASSERT
		assertNotNull(dataInitializerService);
		assertNotNull(stateRuleService);
		assertNotNull(taxCategoryService);
		assertNotNull(stateService);
		assertNotNull(validationService);
		assertNotNull(validationResultTransformer);
		assertNotNull(stateRuleTransformer);
		assertNotNull(controller);
		assertNotNull(exceptionHandler);
		assertNotNull(GlobalDaoHolder.getTaxCategoryDao());
		assertNotNull(GlobalDaoHolder.getStateDao());
	}

	@Test
	public void nonExistingTaxCategoryValidationTest() {
		// ARRANGE
		assertNotNull(validationService);
		assertNotNull(GlobalDaoHolder.getTaxCategoryDao());

		TaxCategory taxCategory = new TaxCategory();
		taxCategory.setName("nonExisting");

		// ACT - validate the tax category using a specific validation groups: a Default
		// group + our custom group
		List<ValidationResult> constraintViolations = validationService.validate(taxCategory, Default.class,
				TaxCategoryShouldExist.class);

		// ASSERT
		assertNotNull(constraintViolations);
		assertEquals(2, constraintViolations.size());
		assertTrue(constraintViolations.contains(new ValidationResult("error.category.invalid", "nonExisting", "name")));
		assertTrue(constraintViolations.contains(new ValidationResult("error.missing", null, "id")));
	}

	@Test
	public void existingTaxCategoryValidationTest() {
		// ARRANGE
		TaxCategory taxCategory1 = taxCategoryDao.findById(1L);

		// ACT - validate the tax category using a specific validation groups: a Default
		// group + our custom group
		List<ValidationResult> constraintViolations1 = validationService.validate(taxCategory1, Default.class,
				TaxCategoryShouldExist.class);

		// ASSERT
		assertNotNull(constraintViolations1);
		assertEquals(0, constraintViolations1.size());
	}

	@Test
	public void taxCategoryServiceTest_taxCategoryRulesValidation() {
		// ARRANGE
		PostStateRuleRequest request = new PostStateRuleRequest();
		request.setRules(new HashMap<>());
		// add existing categories
		double i = 0.1;
		for (TaxCategory taxCategory : taxCategoryService.getAll()) {
			request.getRules().put(taxCategory.getName(), i);
			i += 0.01;
		}
		// add not existing categories
		request.getRules().put("non existing one", i);
		i += 0.1;
		request.getRules().put("non existing two", i);

		// ACT - validating map of rules
		List<ValidationResult> constraintViolations = validationService.validate(request, TaxCategoryShouldExist.class);

		// ASSERT
		assertNotNull(constraintViolations);
		assertEquals(2, constraintViolations.size());
		assertTrue(constraintViolations
				.contains(new ValidationResult("error.invalid", request.getRules(), "rules.non existing one")));
		assertTrue(constraintViolations
				.contains(new ValidationResult("error.invalid", request.getRules(), "rules.non existing two")));
	}

	@Test
	public void taxCategoryServiceTest_taxCategotyNameValidation() {
		// ARRANGE
		PostStateRuleRequest request = new PostStateRuleRequest();
		request.setRules(new HashMap<>());
		// find existing categories
		TaxCategory taxCategory1 = taxCategoryService.findById(1L);
		TaxCategory taxCategory2 = taxCategoryService.findById(2L);

		// add not existing categories
		request.getRules().put("non existing one", 0.1);
		request.getRules().put("non existing two", 0.2);
		request.getRules().put(taxCategory1.getName(), 0.1);
		request.getRules().put(taxCategory2.getName(), null);

		// ACT
		List<ValidationResult> constraintViolations = validationService.validate(request);

		// ASSERT
		assertNotNull(constraintViolations);
		List<Object> errorResponseValues = constraintViolations.stream().map(cv -> cv.getValue())
				.collect(Collectors.toList());

		assertTrue(errorResponseValues.contains("non existing one"));
		assertTrue(errorResponseValues.contains("non existing two"));
		assertTrue(errorResponseValues.contains(null));
	}

	@Test
	public void taxCalculationController_addNewRule_updateExistingRuleTest() {
		
		String stateRulesUpdateCode = "PA";
		// ASSERT INITIAL APP STATE
		assertTrue(stateService.findByCode(stateRulesUpdateCode) != null);
		assertEquals(2, stateRuleService.getStateRule(stateRulesUpdateCode).getTaxRules().size());
		
		// ARRANGE
		StateRulesRequestModel requestModel = new StateRulesRequestModel();
		List<StateRuleModel> modelRules = new ArrayList<StateRulesRequestModel.StateRuleModel>();
		modelRules.add(new StateRuleModel("clothing", 0.16));
		modelRules.add(new StateRuleModel("electronic devices", 0.12));
		modelRules.add(new StateRuleModel("books", 0.10));
		requestModel.setRules(modelRules);

		// ACT
		controller.addNewRule(stateRulesUpdateCode, requestModel);
		
		// ASSERT
		assertEquals(3, stateRuleService.getStateRule(stateRulesUpdateCode).getTaxRules().size());
	}

	@Test
	public void taxCalculationController_addNewRule_addNewRuleTest() {
		
		String newRulesStateCode = "NJ";
		// ASSERT INITIAL APP STATE
		assertNotNull(stateService.findByCode(newRulesStateCode) != null);
		assertNull(stateRuleService.getStateRule(newRulesStateCode));
		
		// ARRANGE
		StateRulesRequestModel requestModel = new StateRulesRequestModel();
		List<StateRuleModel> modelRules = new ArrayList<StateRulesRequestModel.StateRuleModel>();
		modelRules.add(new StateRuleModel("clothing", 0.16));
		modelRules.add(new StateRuleModel("electronic devices", 0.12));
		modelRules.add( new StateRuleModel("books", 0.10));
		requestModel.setRules(modelRules);

		// ACT
		controller.addNewRule(newRulesStateCode, requestModel);
		
		// ASSERT
		assertEquals(3, stateRuleService.getStateRule(newRulesStateCode).getTaxRules().size());
	}
}
