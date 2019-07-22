package com.gridu.microservice.taxes.service;


import com.gridu.microservice.taxes.model.TaxCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaxCategoryService {

	private final List<TaxCategory> TAX_CATEGORIES = new ArrayList<>();
	private AtomicLong id = new AtomicLong(0);

	public List<TaxCategory> getAll() {
		return TAX_CATEGORIES.subList(0, TAX_CATEGORIES.size());
	}

	public TaxCategory saveTaxCategory(TaxCategory taxCategory) {
		if (taxCategory.getId() == null) {
			taxCategory.setId(id.incrementAndGet());
		}
		TAX_CATEGORIES.add(taxCategory);
		return taxCategory;
	}
}
