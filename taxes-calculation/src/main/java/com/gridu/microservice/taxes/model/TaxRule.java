package com.gridu.microservice.taxes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Table(name="state_rule")
@Entity
public class TaxRule {

	@Id
	private Long id;

	@Transient
	@Valid
	@NotNull
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TaxRule that = (TaxRule) o;

		if (taxCategory != null ? !taxCategory.equals(that.taxCategory) : that.taxCategory != null) return false;
		return rule != null ? rule.equals(that.rule) : that.rule == null;
	}

	@Override
	public int hashCode() {
		int result = rule != null ? rule.hashCode() : 0;
		result = 31 * result + (rule != null ? rule.hashCode() : 0);
		return result;
	}
}
