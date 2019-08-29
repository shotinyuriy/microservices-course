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
import com.gridu.microservice.taxes.model.TaxRule;

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
		List<State> stateList = (List<State>) getEntityManager()
				.createNativeQuery("select * from state where code = ?1", State.class).setParameter(1, code)
				.getResultList();
		State state = null;
		if (stateList.size() > 0) {
			state = stateList.get(0);
		}
		StateRule stateRule = new StateRule();
		if (state != null) {
			List<StateRule> stateRuleList = getEntityManager()
					.createNativeQuery("select * from state_rule where state_id = ?1", StateRule.class)
					.setParameter(1, state.getId()).getResultList();
			if (stateRuleList.size() > 0) {
				stateRule = stateRuleList.get(0);
				List<TaxRule> taxRules = (List<TaxRule>) getEntityManager().createNativeQuery("select * from tax_rule where state_rule_id = ?1", TaxRule.class).setParameter(1, stateRule.getId()).getResultList();
				stateRule.setTaxRules(taxRules );
			}

		}
		return stateRule;
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
