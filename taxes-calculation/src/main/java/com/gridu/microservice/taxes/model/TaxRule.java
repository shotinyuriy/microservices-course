package com.gridu.microservice.taxes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Table(name = "tax_rule")
@Entity
public class TaxRule {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="state_rule_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private StateRule stateRule;

	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name="tax_category_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private TaxCategory taxCategory;

	@Min(value = 0, message = "error.taxRule.tooSmall")
	@Max(value = 1, message = "error.taxRule.tooBig")
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

	public StateRule getStateRule() {
		return stateRule;
	}

	public TaxCategory getTaxCategory() {
		return taxCategory;
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

	public void setStateRule(StateRule stateRule) {
		this.stateRule = stateRule;
	}

	public void setTaxCategory(TaxCategory taxCategory) {
		this.taxCategory = taxCategory;
	}
}
