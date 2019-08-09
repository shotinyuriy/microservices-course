package com.gridu.microservice.taxes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Table(name="state_rule")
@Entity
public class StateRule {

	@Id
	private Long id;

	// annotation ensures that validators in State fields will be called on
	// validating StateRule objects
	// ie. provides cascading validation
	@Valid
	@NotNull
	@Transient
	private State state;

	@Transient
	private List<TaxRule> taxRules = new ArrayList<TaxRule>();;

	public StateRule() {
	}

	public StateRule(State state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setTaxRules(List<TaxRule> taxRules) {
		this.taxRules = taxRules;
	}

	public List<TaxRule> getTaxRules() {
		return taxRules;
	}

	public void addTaxRule(TaxRule taxRule) {
		boolean updated = false;
		for (TaxRule rule : taxRules) {
			if (rule.getTaxCategory().equals(taxRule.getTaxCategory())) {
				rule.setRule(taxRule.getRule());
				updated = true;
				break;
			}
		}
		if (!updated) {
			getTaxRules().add(taxRule);
		}
	}
}
