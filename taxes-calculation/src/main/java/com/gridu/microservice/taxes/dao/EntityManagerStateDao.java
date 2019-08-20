package com.gridu.microservice.taxes.dao;

import com.gridu.microservice.taxes.model.State;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Profile("entity-manager")
@Repository
public class EntityManagerStateDao implements StateDao {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public List<State> getAll() {
		List<State> states;

		EntityManager em = getEntityManager();

		Query query = em.createQuery("SELECT s FROM State s", State.class);
		states = query.getResultList();

		return states;
	}

	@Transactional
	@Override
	public State save(State entity) {
		EntityManager em = getEntityManager();

		if (entity.getId() == null) {
			long id = (long) entity.getCode().toUpperCase().hashCode();
			entity.setId(id);
		}

		if (em.contains(entity)) {
			em.merge(entity);
		} else {
			em.persist(entity);
		}
		em.flush();
		return entity;
	}

	@Transactional
	@Override
	public State findById(Long id) {
		EntityManager em = getEntityManager();

		return em.find(State.class, id);
	}

	@Transactional
	@Override
	public State remove(State entity) {
		if (entity.getId() != null) {
			EntityManager em = getEntityManager();
			State state = findById(entity.getId());
			em.remove(state);
			em.flush();
			return state;
		}
		return null;
	}

	@Transactional
	@Override
	public List<State> find(Predicate<State> predicate) {
		return getAll().stream().filter(predicate).collect(Collectors.toList());
	}

	@Override
	public State findByCode(String code) {
		EntityManager em = getEntityManager();
		Query query = em.createQuery("SELECT s FROM State s WHERE s.code = ?1", State.class);
		query.setParameter(1, code);

		List<State> states = query.getResultList();
		return (states == null || states.isEmpty()) ? null : states.get(0);
	}
}
