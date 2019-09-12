package com.gridu.microservice.taxes.dao;

import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.gridu.microservice.taxes.model.State;

@Repository
@Profile("entity-manager")
public class EntityMngrStateDao implements StateDao {


	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<State> find(Predicate<State> predicate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public State findByCode(String code) {
		State state = (State) getEntityManager().createQuery("from State where code = ?").setParameter(0, code)
				.getResultList().get(0);
		return state;
	}

	@Override
	public State findById(Long id) {
		State state = getEntityManager().find(State.class, id);
		return state;
	}

	@Override
	public List<State> getAll() {
		List<State> results = getEntityManager().createQuery("from State").getResultList();
		return results;
	}

	@Override
	@Transactional
	public State save(State entity) {
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
