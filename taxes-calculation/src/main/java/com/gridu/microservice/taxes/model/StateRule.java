package com.gridu.microservice.taxes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Table(name="state_rule")
@Entity
public class StateRule {

	@Id
	private Long id;

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
		getTaxRules().add(taxRule);
	}
}
