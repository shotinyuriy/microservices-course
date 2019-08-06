package com.gridu.microservice.taxes.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TaxRule {

	@Valid
	@NotNull
	private TaxCategory taxCategory;
	private Double rule;

	public TaxRule() {}
	
	public TaxRule(TaxCategory taxCategory, Double rule) {
		this.taxCategory = taxCategory;
		this.rule = rule;
	}
	
	public Double getRule() {
		return rule;
	}

	public void setRule(Double rule) {
		this.rule = rule;
	}

	public TaxCategory getTaxCategory() {
		return taxCategory;
	}

	public void setTaxCategory(TaxCategory taxCategory) {
		this.taxCategory = taxCategory;
	}
}
