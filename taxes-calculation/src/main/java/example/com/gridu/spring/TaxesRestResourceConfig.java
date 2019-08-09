package example.com.gridu.spring;

import com.gridu.microservice.taxes.rest.TaxesCalculationRestResourceV1;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxesRestResourceConfig {

	@Bean
	public TaxesCalculationRestResourceV1 taxesCalculationRestResourceV1() {
		TaxesCalculationRestResourceV1 taxesCalculationRestResourceV1 = new TaxesCalculationRestResourceV1();
		return taxesCalculationRestResourceV1;
	}

	@Bean
	public StateService stateService() {
		StateService stateService = new StateService();
		return stateService;
	}

	@Bean
	public TaxCategoryService taxCategoryService() {
		TaxCategoryService taxCategoryService = new TaxCategoryService();
		return taxCategoryService;
	}

	@Bean
	public StateRuleTransformer stateRuleTransformer() {
		StateRuleTransformer stateRuleTransformer = new StateRuleTransformer();
		return stateRuleTransformer;
	}
}
