package com.gridu.microservice.taxes.dao;

import com.gridu.microservice.taxes.model.StateRule;

public interface StateRuleDao extends GenericDao<Long, StateRule> {

	StateRule findByCode(String code);
}
