package com.gridu.microservice.taxes.dao;

import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.gridu.microservice.taxes.model.TaxCategory;

@Repository
@Profile("entity-manager")
public class EntityMngrTaxCategoryDao  implements TaxCategoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TaxCategory> find(Predicate<TaxCategory> predicate) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public TaxCategory findByCategory(String category) {
		List<TaxCategory> taxCategory = (List<TaxCategory>) getEntityManager().createNativeQuery("select * from tax_category where name = ?1", TaxCategory.class)
				.setParameter(1, category).getResultList();
		return taxCategory.size() > 0 ? taxCategory.get(0) : null;
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
