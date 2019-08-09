package example.com.gridu.spring;

import com.gridu.microservice.taxes.dao.InMemoryStateDao;
import com.gridu.microservice.taxes.dao.InMemoryStateRuleDao;
import com.gridu.microservice.taxes.dao.InMemoryTaxCategoryDao;
import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.dao.StateRuleDao;
import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import com.gridu.microservice.taxes.rest.TaxesCalculationRestResourceV1;
import com.gridu.microservice.taxes.rest.transformer.StateRuleTransformer;
import com.gridu.microservice.taxes.rest.transformer.ValidationResultTransformer;
import com.gridu.microservice.taxes.service.DataInitializerService;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.validation.ValidationService;
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
	public StateRuleService stateRuleService() {
		StateRuleService stateRuleService = new StateRuleService();
		return stateRuleService;
	}

	@Bean
	public ValidationService validationService() {
		ValidationService validationService = new ValidationService();
		return validationService;
	}

	@Bean
	public StateRuleTransformer stateRuleTransformer() {
		StateRuleTransformer stateRuleTransformer = new StateRuleTransformer();
		return stateRuleTransformer;
	}

	@Bean
	public ValidationResultTransformer validationResultTransformer() {
		ValidationResultTransformer transformer = new ValidationResultTransformer();
		return transformer;
	}

	@Bean
	public DataInitializerService dataInitializerService() {
		DataInitializerService dataInitializerService = new DataInitializerService();
		return dataInitializerService;
	}

	@Bean
	public StateDao stateDao() {
		StateDao stateDao = new InMemoryStateDao();
		return stateDao;
	}

	@Bean
	public TaxCategoryDao taxCategoryDao() {
		TaxCategoryDao taxCategoryDao = new InMemoryTaxCategoryDao();
		return taxCategoryDao;
	}

	@Bean
	public StateRuleDao stateRuleDao() {
		StateRuleDao stateRuleDao = new InMemoryStateRuleDao();
		return stateRuleDao;
	}
}
