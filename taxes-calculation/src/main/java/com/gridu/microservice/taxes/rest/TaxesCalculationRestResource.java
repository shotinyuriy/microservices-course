package com.gridu.microservice.taxes.rest;

import java.util.ArrayList;
import java.util.List;

import com.gridu.microservice.taxes.service.TaxCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservice.taxes.model.TaxCategory;

@RestController
public class TaxesCalculationRestResource {

	@Autowired
	private TaxCategoryService taxCategoryService;
	
	@GetMapping(value = "/taxes/categories", produces = "application/json")
	public List<TaxCategory> getTaxesCategories() {
		return getTaxCategoryService().getAll();
	}

	public TaxCategoryService getTaxCategoryService() {
		return taxCategoryService;
	}

	public void setTaxCategoryService(TaxCategoryService taxCategoryService) {
		this.taxCategoryService = taxCategoryService;
	}
}
