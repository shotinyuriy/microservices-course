package com.gridu.microservice.taxes.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name="state_rule")
@Entity
public class StateRule {

	@Id
	private Long id;
	
	//provides cascading validation
	@Valid
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	private State state;

	@Cascade(CascadeType.ALL)
	@OneToMany(mappedBy = "stateRule", fetch = FetchType.EAGER)
	private List<TaxRule> taxRules = new ArrayList<TaxRule>();

	public StateRule() {
	}

	public StateRule(State state) {
		this.state = state;
	}

	public void addTaxRule(TaxRule taxRule) {
		boolean updated = false;
		for (TaxRule rule : taxRules) {
			if (rule.getTaxCategory() != null && rule.getTaxCategory().equals(taxRule.getTaxCategory())) {
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
