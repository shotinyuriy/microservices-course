package com.gridu.microservice.taxes.dao;

import com.gridu.microservice.taxes.model.TaxCategory;
import org.hibernate.Session;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Predicate;

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
		TaxCategory taxCategory = (TaxCategory) getEntityManager().createQuery("from TaxCategory where name = ?1")
				.setParameter(1, category).getResultList().get(0);
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
