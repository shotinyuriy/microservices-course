package com.gridu.microservice.taxes.service;

import com.gridu.microservice.taxes.model.State;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StateService {

	private final List<State> STATES = new ArrayList<>();
	private AtomicLong id = new AtomicLong(0);

	public List<State> getAll() {
		return STATES.subList(0, STATES.size());
	}

	public State saveState(State taxCategory) {
		if (taxCategory.getId() == null) {
			taxCategory.setId(id.incrementAndGet());
		}
		STATES.add(taxCategory);
		return taxCategory;
	}

	public State findByCode(String code) {
		// TODO: implement this
		return null;
	}
}
