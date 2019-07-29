package com.gridu.microservice.taxes.dao;

import com.gridu.microservice.taxes.model.State;

public interface StateDao extends GenericDao<Long, State> {

	State findByCode(String code);
}