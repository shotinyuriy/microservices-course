package com.gridu.microservice.taxes.dao;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface GenericDao<K, T> {

	List<T> getAll();
	T save(T entity);
	T findById(K id);
	List<T> find(Predicate<T> predicate);
}
