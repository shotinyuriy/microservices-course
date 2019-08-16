package com.gridu.microservice.taxes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gridu.microservice.taxes.dao.StateRuleDao;
import com.gridu.microservice.taxes.model.StateRule;

@Service
public class StateRuleService {

	@Autowired
	private StateRuleDao stateRuleDao;

	private StateRuleDao getStateRuleDao() {
		return stateRuleDao;
	}

	public List<StateRule> getAll() {
		return  getStateRuleDao().getAll();
	}

	public StateRule saveStateRule(StateRule stateRule) {
		return getStateRuleDao().save(stateRule);
	}
	
	public StateRule getStateRule(String stateCode) {
		return getStateRuleDao().findByCode(stateCode);
	}

	public StateRule getStateRule(long id) {
		return getStateRuleDao().findById(id);
	}

	public void removeSateRule(StateRule stateRule) {
		getStateRuleDao().remove(stateRule);
	}
}
