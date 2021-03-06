package com.gridu.microservice.taxes.service;

import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import com.gridu.microservice.taxes.model.TaxCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxCategoryService {

	@Autowired
	private TaxCategoryDao taxCategoryDao;

	public TaxCategory findByCategory(String category) {
		return getTaxCategoryDao().findByCategory(category);
	}

	public TaxCategory findById(Long id) {
		return getTaxCategoryDao().findById(id);
	}

	public List<TaxCategory> getAll() {
		return getTaxCategoryDao().getAll();
	}

	public TaxCategory saveTaxCategory(TaxCategory taxCategory) {
		return getTaxCategoryDao().save(taxCategory);
	}

	public void setTaxCategoryDao(TaxCategoryDao taxCategoryDao) {
		this.taxCategoryDao = taxCategoryDao;
	}
	
	private TaxCategoryDao getTaxCategoryDao() {
		return taxCategoryDao;
	}
}
