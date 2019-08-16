package com.gridu.microservice.taxes.dao;

import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.gridu.microservice.taxes.model.TaxCategory;

@Repository
@Profile("entity-manager")
public class EntityMngrTaxCategoryDao  implements TaxCategoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	//TO BE FIXED
	@Override
	public List<TaxCategory> find(Predicate<TaxCategory> predicate) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public TaxCategory findByCategory(String category) {
		TaxCategory taxCategory = (TaxCategory) getEntityManager().createQuery("from TaxCategory where name = ?")
				.setParameter(0, category).getResultList().get(0);
		return taxCategory;
	}

	@Override
	public TaxCategory findById(Long id) {
		TaxCategory taxCategory = getEntityManager().find(TaxCategory.class, id);
		return taxCategory;
	}

	@Override
	public List<TaxCategory> getAll() {
		List<TaxCategory> results = getEntityManager().createQuery("from TaxCategory").getResultList();
		return results;
	}

	@Override
	@Transactional
	public TaxCategory save(TaxCategory entity) {
		getEntityManager().persist(entity);
		return entity;
	}

	private EntityManager getEntityManager() {
		return entityManager;
	}

}
