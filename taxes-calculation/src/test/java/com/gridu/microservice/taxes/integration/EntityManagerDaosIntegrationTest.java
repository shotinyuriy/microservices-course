package com.gridu.microservice.taxes.integration;

import com.gridu.microservice.taxes.dao.EntityManagerStateDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("entity-manager")
@ContextConfiguration("file:src/main/webapp/config/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityManagerDaosIntegrationTest {

	@Autowired
	protected EntityManagerStateDao stateDao;

	public void test() {

	}
}
