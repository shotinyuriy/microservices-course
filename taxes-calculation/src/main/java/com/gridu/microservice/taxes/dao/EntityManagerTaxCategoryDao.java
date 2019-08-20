package com.gridu.microservice.taxes.dao;

import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
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
public class EntityManagerTaxCategoryDao implements TaxCategoryDao {

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
		Query query = em.createQuery("SELECT sr  FROM TaxCategory sr WHERE sr.id = (SELECT MAX(sr1.id) FROM TaxCategory sr1)", TaxCategory.class);
		List<TaxCategory> taxRules = query.getResultList();
		if (taxRules != null && !taxRules.isEmpty()) return taxRules.get(0).getId() + 1L;
		return 1L;
	}

	@Override
	public List<TaxCategory> getAll() {
		List<TaxCategory> taxCategories;

		EntityManager em = getEntityManager();
		Query query = em.createQuery("SELECT tc FROM TaxCategory tc", TaxCategory.class);
		taxCategories = query.getResultList();

		return  taxCategories;
	}

	@Transactional
	@Override
	public TaxCategory save(TaxCategory entity) {
		EntityManager em = getEntityManager();

		if (em.contains(entity)) {
			em.merge(entity);
		} else {
			Long id = getNextId();
			entity.setId(id);
			em.persist(entity);
		}
		em.flush();
		return entity;
	}

	@Transactional
	@Override
	public TaxCategory findById(Long id) {
		EntityManager em = getEntityManager();
		return em.find(TaxCategory.class, id);
	}

	@Transactional
	@Override
	public TaxCategory remove(TaxCategory entity) {
		if (entity.getId() != null) {
			EntityManager em = getEntityManager();
			TaxCategory taxCategory = findById(entity.getId());
			em.remove(taxCategory);
			em.flush();
			return taxCategory;
		}
		return null;
	}

	@Transactional
	@Override
	public List<TaxCategory> find(Predicate<TaxCategory> predicate) {
		return getAll().stream().filter(predicate).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public TaxCategory findByCategory(String category) {
		List<TaxCategory> taxCategories;

		EntityManager em = getEntityManager();
		Query query = em.createQuery("SELECT tc FROM TaxCategory tc WHERE tc.name = ?1", TaxCategory.class);
		query.setParameter(1, category);
		taxCategories = query.getResultList();

		return  (taxCategories == null || taxCategories.isEmpty()) ? null : taxCategories.get(0);
	}
}
