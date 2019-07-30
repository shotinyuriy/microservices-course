package com.gridu.microservice.taxes.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.gridu.microservice.taxes.model.StateRule;

@Repository
public class InMemoryStateRuleDao implements StateRuleDao {

	private final Map<Long, StateRule> STATE_RULES = new ConcurrentHashMap<>();
	private AtomicLong id = new AtomicLong(0);

	@Override
	public List<StateRule> getAll() {
		List<StateRule> stateRules = new ArrayList<StateRule>();
		stateRules.addAll(STATE_RULES.values());
		return stateRules;
	}

	@Override
	public StateRule save(StateRule stateRule) {
		if (stateRule.getId() == null) {
			stateRule.setId(id.incrementAndGet());
		}
		STATE_RULES.put(stateRule.getId(), stateRule);
		return stateRule;
	}

	@Override
	public StateRule findById(Long id) {
		return STATE_RULES.get(id);
	}

	@Override
	public List<StateRule> find(Predicate<StateRule> predicate) {
		return STATE_RULES.values().stream()
				.filter(predicate)
				.collect(Collectors.toList());
	}

	@Override
	public StateRule findByCode(String stateCode) {
		Predicate<StateRule> stateRuleByCode = p -> p.getState().getCode().equals(stateCode);
		return STATE_RULES.values().stream().filter(stateRuleByCode).findFirst().orElseGet(() -> null);
	}

}
