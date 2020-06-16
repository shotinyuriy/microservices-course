package com.gridu.microservices.product.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.gridu.microservices.product.catalog.dao.CurrencyRepository;
import com.gridu.microservices.product.catalog.model.Currency;

@Service
public class CurrencyService extends GenericRepositoryService<Currency, Long> {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	protected CrudRepository<Currency, Long> getRepository() {
		return currencyRepository;
	}

	public Currency findByValue(String value) {
		return currencyRepository.findByValue(value);
	}
}
