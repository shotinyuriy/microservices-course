package com.gridu.microservice.taxes.rest.model;


import com.gridu.microservice.taxes.validation.annotation.ExistingTaxCategoryMap;

import java.util.Map;

public class PostStateRuleRequest {

	@ExistingTaxCategoryMap
	private Map<String, Double> rules;

	public Map<String, Double> getRules() {
		return rules;
	}

	public void setRules(Map<String, Double> rules) {
		this.rules = rules;
	}
}
