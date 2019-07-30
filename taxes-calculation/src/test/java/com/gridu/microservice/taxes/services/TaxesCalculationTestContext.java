package com.gridu.microservice.taxes.services;

import static org.junit.Assert.*;

import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.validation.ErrorResponse;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.groups.TaxCategoryShouldExist;
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
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;


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
	
	@Test
	public void test() {
		
		assertNotNull(dataInitializerService);
		assertNotNull(stateRuleService);
		assertNotNull(taxCategoryService);
		assertNotNull(stateService);

		
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
}
