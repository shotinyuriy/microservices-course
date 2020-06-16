package com.gridu.microservices.product.catalog.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gridu.microservices.product.catalog.model.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

	//selectively exposed CRUD methods
	Currency findByValue(String value);
}
