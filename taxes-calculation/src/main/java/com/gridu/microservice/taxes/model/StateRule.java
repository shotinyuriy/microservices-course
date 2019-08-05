package com.gridu.microservice.taxes.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

public class StateRule {
	
	private Long id;

	//annotation ensures that validators in State fields will be called on validating StateRule objects
	//ie. provides cascading validation
	@Valid
	private State state;
	
	@Valid
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
