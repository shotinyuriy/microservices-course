package com.gridu.microservice.taxes.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Table(name="state_rule")
@Entity
public class StateRule {

	@Id @GeneratedValue
	private Long id;
	
	//provides cascading validation
	@Valid
	@NotNull
	@OneToOne
	@JoinColumn(name="state_id")
	@NotFound(action = NotFoundAction.EXCEPTION)
	private State state;

  @Valid
	@OneToMany(mappedBy = "stateRule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<TaxRule> taxRules = new ArrayList<TaxRule>();

	public StateRule() {
	}

	public StateRule(State state) {
		this.state = state;
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
			taxRule.setStateRule(this);
			getTaxRules().add(taxRule);
		}
	}

	public Long getId() {
		return id;
	}

	public State getState() {
		return state;
	}

	public Double getTax(String category) {
		Double tax = 0.0;
		for (TaxRule taxRule : taxRules) {
			if (taxRule.getTaxCategory().getName().equals(category)) {
				tax = taxRule.getRule();
			}
		}
		return tax;
	}

	public Collection<TaxRule> getTaxRules() {
		return taxRules;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setTaxRules(List<TaxRule> taxRules) {
		this.taxRules = taxRules;
	}
}
