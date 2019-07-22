package com.gridu.microservice.taxes.service;

import com.gridu.microservice.taxes.model.TaxCategory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInitializerService implements InitializingBean {

	@Autowired
	private TaxCategoryService taxCategoryService;

	@Override
	public void afterPropertiesSet() throws Exception {
		getTaxCategoryService().saveTaxCategory(new TaxCategory("books"));
		getTaxCategoryService().saveTaxCategory(new TaxCategory("clothing"));
		getTaxCategoryService().saveTaxCategory(new TaxCategory("electronic devices"));
	}

	public TaxCategoryService getTaxCategoryService() {
		return taxCategoryService;
	}

	public void setTaxCategoryService(TaxCategoryService taxCategoryService) {
		this.taxCategoryService = taxCategoryService;
	}
}
