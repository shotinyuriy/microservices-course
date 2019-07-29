package com.gridu.microservice.taxes.dao;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.gridu.microservice.taxes.model.State;

@Repository
public class InMemoryStateDao implements StateDao {

	private final HashMap<Long, State> STATES = new HashMap<>();
	private AtomicLong id = new AtomicLong(0);

	@Override
	public List<State> getAll() {
		return STATES.values().stream().collect(Collectors.toList());
	}

	@Override
	public State save(State state) {
		if (state.getId() == null) {
			state.setId(id.incrementAndGet());
		}
		STATES.put(state.getId(), state);
		return state;
	}

	@Override
	public State findById(Long id) {
		return STATES.get(id);
	}

	@Override
	public List<State> find(Predicate<State> predicate) {
		return STATES.values().stream().filter(predicate).collect(Collectors.toList());
	}

	@Override
	public State findByCode(String code) {
		Predicate<State> stateByCode = p -> p.getCode().equals(code);
		return STATES.values().stream().filter(stateByCode).findFirst().orElseGet(null); 
	}

}
