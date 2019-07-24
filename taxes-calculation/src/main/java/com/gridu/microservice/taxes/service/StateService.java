package com.gridu.microservice.taxes.service;

import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.model.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StateService {

	@Autowired
	private StateDao stateDao;

	public List<State> getAll() {
		return getStateDao().getAll();
	}

	public State saveState(State state) {
		return getStateDao().save(state);
	}

	public State findByCode(String code) {
		// TODO: implement this
		// getStateDao().method?()
		return null;
	}

	public StateDao getStateDao() {
		return stateDao;
	}

	public void setStateDao(StateDao stateDao) {
		this.stateDao = stateDao;
	}
}
