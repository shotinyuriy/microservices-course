package com.gridu.microservice.taxes.services;

import static org.junit.Assert.*;

import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.rest.model.PostStateRuleRequest;
import com.gridu.microservice.taxes.validation.ErrorResponse;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;
import com.gridu.microservice.taxes.validation.StateValidatorService;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.annotation.ExistingTaxCategoryName;
import com.gridu.microservice.taxes.validation.exception.CustomValidationException;
import com.gridu.microservice.taxes.validation.groups.TaxCategoryShouldExist;
import org.junit.Assert;
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

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@ContextConfiguration("file:src/main/webapp/config/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaxesCalculationTestContext {

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
	private StateValidatorService stateValidatorService;

	@Before
	public void setUp() {
		// ARRANGE + ASSERT
		assertNotNull(dataInitializerService);
		assertNotNull(stateRuleService);
		assertNotNull(taxCategoryService);
		assertNotNull(stateService);
		assertNotNull(validationService);
		assertNotNull(stateValidatorService);
		assertNotNull(GlobalDaoHolder.getTaxCategoryDao());
	}
	
	@Test
	public void test() {
		assertEquals(3, taxCategoryService.getAll().size());
		assertEquals(5, stateService.getAll().size());
		assertEquals(2, stateRuleService.getAll().size());

	}

	@Test
	public void testTaxCategoryValidator() {

		// ARRANGE
		assertNotNull(validationService);
		assertNotNull(GlobalDaoHolder.getTaxCategoryDao());

		// Validating a Non-existing TaxCategory
		TaxCategory taxCategory = new TaxCategory();
		taxCategory.setName("nonExisting");
		// ACT
		// validate the tax category using a specific validation groups: a Default group + our custom group
		List<ErrorResponse> constraintViolations = validationService.validate(taxCategory, Default.class, TaxCategoryShouldExist.class);
		// ASSERT
		assertNotNull(constraintViolations);
		assertEquals(2, constraintViolations.size());
		assertTrue(constraintViolations.contains(new ErrorResponse("name.invalid", "nonExisting")));
		assertTrue(constraintViolations.contains(new ErrorResponse("id.missing", null)));

		// ARRANGE
		// Validating an Existing TaxCategory
		TaxCategory taxCategory1 = taxCategoryDao.findById(1L);
		// ACT
		// validate the tax category using a specific validation groups: a Default group + our custom group
		List<ErrorResponse> constraintViolations1 = validationService.validate(taxCategory1, Default.class, TaxCategoryShouldExist.class);
		// ASSERT
		assertNotNull(constraintViolations1);
		assertEquals(0, constraintViolations1.size());
	}

	@Test
	public void testTaxCategoryMapValidator() {
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

		// ACT
		List<ErrorResponse> constraintViolations = validationService.validate(request, TaxCategoryShouldExist.class);

		// ASSERT
		assertNotNull(constraintViolations);
		assertTrue(constraintViolations.contains(new ErrorResponse("rules.non existing one.invalid", request.getRules())));
		assertTrue(constraintViolations.contains(new ErrorResponse("rules.non existing two.invalid", request.getRules())));
	}

	@Test
	public void testTaxCategoryNameValidator_TypeUse() {
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
		List<ErrorResponse> constraintViolations = validationService.validate(request);

		// ASSERT
		assertNotNull(constraintViolations);
		List<Object> errorResponseValues = constraintViolations.stream().map(cv -> cv.getValue()).collect(Collectors.toList());

		assertTrue(errorResponseValues.contains("non existing one"));
		assertTrue(errorResponseValues.contains("non existing two"));
		assertTrue(errorResponseValues.contains(null));
	}

	@Test
	public void testStateValidatorService_ExistingStateCode() throws CustomValidationException {
		String existingStateCode = stateService.getAll().get(0).getCode();

		stateValidatorService.validateStateCodeExists(existingStateCode);

		// PASSES IF THERE ARE NO EXCEPTIONS
	}

	@Test(expected = CustomValidationException.class)
	public void testStateValidatorService_NonExistingStateCode() throws CustomValidationException {
		String stateCode = "ZZ";

		stateValidatorService.validateStateCodeExists(stateCode);
	}
}