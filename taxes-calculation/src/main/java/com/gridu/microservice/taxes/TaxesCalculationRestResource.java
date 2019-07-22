package com.gridu.microservice.taxes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gridu.microservice.taxes.model.TaxCategory;

@RestController
public class TaxesCalculationRestResource {
	
	@GetMapping("/taxes/categories")
	public List<TaxCategory> getTaxesCategories() {
		List<TaxCategory> taxesCategories = new ArrayList<TaxCategory>();
		for(TaxCategory taxCategory : TaxCategory.values()) {
			taxesCategories.add(taxCategory);
		}
		return taxesCategories;
	}
	
}
