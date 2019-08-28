package com.gridu.microservices.product.catalog.service;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.gridu.microservices.product.catalog.model.Product;

public abstract class GenericRepositoryService<T, ID> {
	
	protected abstract CrudRepository<T, ID> getRepository();

	public T save(T entity) {
		return getRepository().save(entity);
	}

	public Iterable<T> saveAll(Iterable<T> entities) {
		return getRepository().saveAll(entities);
	}

	public Optional<T> findById(ID id) {
		return getRepository().findById(id);
	}

	public boolean existsById(ID id) {
		return getRepository().existsById(id);
	}

	public Iterable<T> findAll() {
		return getRepository().findAll();
	}

	public Iterable<T> findAllById(Iterable<ID> ids) {
		return getRepository().findAllById(ids);
	}

	public long count() {
		return getRepository().count();
	}
	
	public void deleteById(ID id) {
		getRepository().deleteById(id);
	}
	
	public void delete(T entity) {
		getRepository().delete(entity);
	}
	
	public void deleteAll(Iterable<? extends T> entities) { 
		getRepository().deleteAll(entities);
	}
	public void deleteAll() {
		getRepository().deleteAll();
	}
}
