package com.gridu.microservice.taxes.model;

import java.util.ArrayList;
import java.util.List;

public class StateRule {

	private Long id;
	private State state;
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
