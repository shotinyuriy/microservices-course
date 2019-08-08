package com.gridu.microservice.taxes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="state_rule")
@Entity
public class TaxRule {

	@Id
	private Long id;

	@Transient
	private TaxCategory taxCategory;

	private Double rule;

	public TaxRule() {}
	
	public TaxRule(TaxCategory taxCategory, Double rule) {
		this.taxCategory = taxCategory;
		this.rule = rule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
