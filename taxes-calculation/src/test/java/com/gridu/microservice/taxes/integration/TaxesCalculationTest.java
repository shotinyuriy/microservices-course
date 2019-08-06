package com.gridu.microservice.taxes.integration;

import static org.junit.Assert.*;

import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.rest.model.PostStateRuleRequest;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;
import com.gridu.microservice.taxes.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.group.TaxCategoryShouldExist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gridu.microservice.taxes.service.DataInitializerService;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;


import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;


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

	@Before
	public void setUp() {
		// ARRANGE + ASSERT
		assertNotNull(dataInitializerService);
		assertNotNull(stateRuleService);
		assertNotNull(taxCategoryService);
		assertNotNull(stateService);
		assertNotNull(validationService);
		assertNotNull(GlobalDaoHolder.getTaxCategoryDao());
	}
	
	@Test
	public void test() {
		assertEquals(3, taxCategoryService.getAll().size());
		assertEquals(5, stateService.getAll().size());
		assertEquals(2, stateRuleService.getAll().size());

	}
	
	@Test
	public void nonExistingTaxCategoryValidationTest() {
		// ARRANGE
		assertNotNull(validationService);
		assertNotNull(GlobalDaoHolder.getTaxCategoryDao());

		TaxCategory taxCategory = new TaxCategory();
		taxCategory.setName("nonExisting");
		
		// ACT - validate the tax category using a specific validation groups: a Default group + our custom group
		List<ValidationResult> constraintViolations = validationService.validate(taxCategory, Default.class, TaxCategoryShouldExist.class);
		
		// ASSERT
		assertNotNull(constraintViolations);
		assertEquals(1, constraintViolations.size());
		assertTrue(constraintViolations.contains(new ValidationResult("name.invalid", "nonExisting")));
	}

	@Test
	public void existingTaxCategoryValidationTest() {
		// ARRANGE
		TaxCategory taxCategory1 = taxCategoryDao.findById(1L);
		
		// ACT - validate the tax category using a specific validation groups: a Default group + our custom group
		List<ValidationResult> constraintViolations1 = validationService.validate(taxCategory1, Default.class, TaxCategoryShouldExist.class);
		
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
			i+= 0.01;
		}
		// add not existing categories
		request.getRules().put("non existing one", i);
		i+=0.1;
		request.getRules().put("non existing two", i);

		// ACT - validating map of rules
		List<ValidationResult> constraintViolations = validationService.validate(request, TaxCategoryShouldExist.class);

		// ASSERT
		assertNotNull(constraintViolations);
		assertTrue(constraintViolations.contains(new ValidationResult("rules.non existing one.invalid", request.getRules())));
		assertTrue(constraintViolations.contains(new ValidationResult("rules.non existing two.invalid", request.getRules())));
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
		List<Object> errorResponseValues = constraintViolations.stream().map(cv -> cv.getValue()).collect(Collectors.toList());

		assertTrue(errorResponseValues.contains("non existing one"));
		assertTrue(errorResponseValues.contains("non existing two"));
		assertTrue(errorResponseValues.contains(null));
	}
}
