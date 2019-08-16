package com.gridu.microservice.taxes.dao;

import java.util.List;
import java.util.function.Predicate;

public interface GenericDao<K, T> {

	List<T> getAll();
	T save(T entity);
	T findById(K id);
	T remove(T entity);
	List<T> find(Predicate<T> predicate);
}
