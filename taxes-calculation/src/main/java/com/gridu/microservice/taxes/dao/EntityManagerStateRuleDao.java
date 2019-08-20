package com.gridu.microservice.taxes.dao;

import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxRule;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Profile("entity-manager")
@Repository
public class EntityManagerStateRuleDao implements StateRuleDao {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	private Long getNextId() {
		EntityManager em = getEntityManager();
		Query query = em.createQuery("SELECT sr  FROM StateRule sr WHERE sr.id = (SELECT MAX(sr1.id) FROM StateRule sr1)");
		List<StateRule> stateRules = query.getResultList();
		if (stateRules != null && !stateRules.isEmpty()) return stateRules.get(0).getId() + 1L;
		return 1L;
	}

	@Transactional
	private Long getNextTaxRuleId() {
		EntityManager em = getEntityManager();
		Query query = em.createQuery("SELECT sr  FROM TaxRule sr WHERE sr.id = (SELECT MAX(sr1.id) FROM TaxRule sr1)");
		List<TaxRule> stateRules = query.getResultList();
		if (stateRules != null && !stateRules.isEmpty()) return stateRules.get(0).getId() + 1L;
		return 1L;
	}

	@Transactional
	@Override
	public List<StateRule> getAll() {
		List<StateRule> stateRules;

		EntityManager em = getEntityManager();
		Query query = em.createQuery("SELECT sr FROM StateRule sr", StateRule.class);
		stateRules = query.getResultList();

		return  stateRules;
	}

	@Transactional
	@Override
	public StateRule save(StateRule entity) {
		EntityManager em = getEntityManager();
		if (entity.getId() != null) {
			StateRule managedStateRule = findById(entity.getId());
			if (managedStateRule != null) {
				em.merge(entity);
			}
		} else {
			entity.setId(getNextId());
			em.persist(entity);
		}
		em.flush();
		return entity;
	}


	@Transactional
	@Override
	public StateRule findById(Long id) {
		EntityManager em = getEntityManager();
		StateRule stateRule = em.find(StateRule.class, id);
		return stateRule;
	}

	@Transactional
	@Override
	public StateRule remove(StateRule entity) {
		if (entity.getId() != null) {
			EntityManager em = getEntityManager();
			StateRule stateRule = findById(entity.getId());
			em.remove(stateRule);
			em.flush();
			return  stateRule;
		}
		return null;
	}

	@Transactional
	@Override
	public List<StateRule> find(Predicate<StateRule> predicate) {
		return getAll().stream().filter(predicate).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public StateRule findByCode(String code) {
		EntityManager em = getEntityManager();
		Query query = em.createQuery("SELECT sr FROM StateRule sr WHERE sr.state.code = ?1", StateRule.class);
		query.setParameter(1, code);
		List<StateRule> stateRules = query.getResultList();

		StateRule stateRule = (stateRules == null || stateRules.isEmpty()) ? null : stateRules.get(0);

		return stateRule;
	}
}
