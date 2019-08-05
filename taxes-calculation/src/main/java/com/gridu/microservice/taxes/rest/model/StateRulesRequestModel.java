package com.gridu.microservice.taxes.rest.model;

import java.util.List;

public class StateRulesRequestModel {

	private List<StateRuleModel> rules;

	public List<StateRuleModel> getRules() {
		return rules;
	}

	public void setRules(List<StateRuleModel> rules) {
		this.rules = rules;
	}
	
	public static class StateRuleModel {
		
		private String category;
		private Double tax;
		
		public String getCategory() {
			return category;
		}
		public Double getTax() {
			return tax;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public void setTax(Double tax) {
			this.tax = tax;
		}
	}
	
	
}
