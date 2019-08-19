package com.gridu.microservice.taxes.dao;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.model.StateRule;

@Repository
@Profile("entity-manager")
public class EntityMngrStateRuleDao implements StateRuleDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<StateRule> find(Predicate<StateRule> predicate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public StateRule findByCode(String code) {
		State state = (State) getEntityManager().createQuery("from State where code = ?").setParameter(0, code)
				.getResultList().get(0);
		List<StateRule> stateRule = getEntityManager().createQuery("from StateRule where state_id = ?")
				.setParameter(0, state.getId()).getResultList();
		return stateRule.isEmpty() ? null : stateRule.get(0);
	}

	@Override
	public StateRule findById(Long id) {
		StateRule stateRule = getEntityManager().find(StateRule.class, id);
		return stateRule;
	}

	@Override
	public List<StateRule> getAll() {
		List<StateRule> results = getEntityManager().createQuery("from StateRule").getResultList();
		return results;
	}

	@Override
	@Transactional
	public StateRule save(StateRule entity) {
		Session session = getEntityManager().unwrap(Session.class);
		Long id = (Long) session.save(entity);
		session.close();
		entity.setId(id);
		return entity;
	}

	private EntityManager getEntityManager() {
		return entityManager;
	}
}
