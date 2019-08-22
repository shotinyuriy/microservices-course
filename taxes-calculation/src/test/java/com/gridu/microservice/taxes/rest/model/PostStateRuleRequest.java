package com.gridu.microservice.taxes.rest.model;


import com.gridu.microservice.taxes.validation.annotation.ExistingTaxCategoryMap;
import com.gridu.microservice.taxes.validation.annotation.ExistingTaxCategoryName;
import com.gridu.microservice.taxes.validation.group.TaxCategoryShouldExist;

import javax.validation.constraints.NotNull;
import java.util.Map;

import static com.gridu.microservice.rest.validation.ValidationErrorType.MISSING;

public class PostStateRuleRequest {

	@ExistingTaxCategoryMap(groups = TaxCategoryShouldExist.class)
	private Map <@ExistingTaxCategoryName String,
				@NotNull(message = MISSING) Double> rules;

	public Map<String, Double> getRules() {
		return rules;
	}

	public void setRules(Map<String, Double> rules) {
		this.rules = rules;
	}
}
