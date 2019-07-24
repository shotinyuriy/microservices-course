package com.gridu.microservice.taxes.service;


import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import com.gridu.microservice.taxes.model.TaxCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaxCategoryService {

	@Autowired
	private TaxCategoryDao taxCategoryDao;

	public List<TaxCategory> getAll() {
		return getTaxCategoryDao().getAll();
	}

	public TaxCategory saveTaxCategory(TaxCategory taxCategory) {
		return getTaxCategoryDao().save(taxCategory);
	}

	public TaxCategoryDao getTaxCategoryDao() {
		return taxCategoryDao;
	}

	public void setTaxCategoryDao(TaxCategoryDao taxCategoryDao) {
		this.taxCategoryDao = taxCategoryDao;
	}
	
	
}
