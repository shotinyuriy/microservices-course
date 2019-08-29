package example.com.gridu.spring;


import com.gridu.microservice.taxes.TaxesCalculationApplication;
import com.gridu.microservice.taxes.rest.TaxesCalculationRestResourceV1;
import com.gridu.microservice.taxes.view.StateRuleViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(
	       classes = TaxesCalculationApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("entity-manager")
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
