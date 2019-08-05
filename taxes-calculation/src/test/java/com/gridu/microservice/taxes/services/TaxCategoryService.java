package com.gridu.microservice.taxes.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.util.TestContext;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes={TaxCategoryService.class})
@ContextConfiguration("file:src/main/webapp/config/application-context.xml")
public class TaxCategoryService {

	@Autowired
	private StateService stateService;
	
	@Before
	public void before() {
		
	}
	@Test
	public void test() {
		assertNotNull(stateService);
	}

}
