package com.gridu.microservice.taxes.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Table(name = "tax_rule")
@Entity
public class TaxRule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "tax_category_id")
	private TaxCategory taxCategory;

	@Cascade(CascadeType.ALL)
	@ManyToOne
	@JoinColumn(name="state_rule_id")
	private StateRule stateRule;

	private Double rule;

	public TaxRule() {
	}

	public TaxRule(TaxCategory taxCategory, Double rule) {
		this.taxCategory = taxCategory;
		this.rule = rule;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TaxRule that = (TaxRule) o;

		if (taxCategory != null ? !taxCategory.equals(that.taxCategory) : that.taxCategory != null)
			return false;
		return rule != null ? rule.equals(that.rule) : that.rule == null;
	}

	public Long getId() {
		return id;
	}

	public Double getRule() {
		return rule;
	}

	public TaxCategory getTaxCategory() {
		return taxCategory;
	}

	public void setTaxCategory(TaxCategory taxCategory) {
		this.taxCategory = taxCategory;
	}

	public StateRule getStateRule() {
		return stateRule;
	}

	public void setStateRule(StateRule stateRule) {
		this.stateRule = stateRule;
	}

	@Override
	public int hashCode() {
		int result = rule != null ? rule.hashCode() : 0;
		result = 31 * result + (rule != null ? rule.hashCode() : 0);
		return result;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRule(Double rule) {
		this.rule = rule;
	}

}
