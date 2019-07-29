package com.gridu.microservice.taxes.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gridu.microservice.taxes.service.DataInitializerService;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;


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

}
