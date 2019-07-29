package com.gridu.microservice.taxes.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StateRuleViewModel {

	private Long id;
	private String state;
	private List<Map<String, Double>> rules = new ArrayList<Map<String,Double>>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Map<String, Double>> getRules() {
		return rules;
	}
	public void setRules(List<Map<String, Double>> rules) {
		this.rules = rules;
	}
	
	public void addRule(Map<String, Double> rule) {
		getRules().add(rule);
	}
	
}
