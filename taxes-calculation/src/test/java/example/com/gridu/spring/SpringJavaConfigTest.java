package example.com.gridu.spring;


import com.gridu.microservice.taxes.rest.TaxesCalculationRestResourceV1;
import com.gridu.microservice.taxes.view.StateRuleViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Ignore
@ActiveProfiles("inmemory")
@ContextConfiguration(classes = {TaxesRestResourceConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringJavaConfigTest {

	@Autowired
	TaxesCalculationRestResourceV1 taxesCalculationRestResourceV1;

	@Before
	public void assertContext() {
		Assert.assertNotNull(taxesCalculationRestResourceV1);
	}

	@Test
	public void testTaxesCalculationRestResourceV1_GetStateRules() {
		ResponseEntity<List<StateRuleViewModel>> responseEntity = taxesCalculationRestResourceV1.getStateRules();

		Assert.assertNotNull(responseEntity);
		Assert.assertNotNull(responseEntity.getBody());
		List<StateRuleViewModel> body = responseEntity.getBody();
		Assert.assertTrue(body.size() > 0);
	}
}
