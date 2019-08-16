package com.gridu.microservice.taxes.dao;

import com.gridu.microservice.taxes.model.TaxCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaxCategoryDao implements TaxCategoryDao {

	private final Map<Long, TaxCategory> TAX_CATEGORIES = new ConcurrentHashMap<>();
	private AtomicLong id = new AtomicLong(0);

	@Override
	public List<TaxCategory> getAll() {
		List<TaxCategory> taxCategories = new ArrayList<>();
		taxCategories.addAll(TAX_CATEGORIES.values());
		return taxCategories;
	}

	@Override
	public TaxCategory save(TaxCategory taxCategory) {
		if (taxCategory.getId() == null) {
			taxCategory.setId(id.incrementAndGet());
		}
		TAX_CATEGORIES.put(taxCategory.getId(), taxCategory);
		return taxCategory;
	}

	@Override
	public TaxCategory findById(Long id) {
		return TAX_CATEGORIES.get(id);
	}

	@Override
	public List<TaxCategory> find(Predicate<TaxCategory> predicate) {
		return TAX_CATEGORIES.values().stream()
				.filter(predicate)
				.collect(Collectors.toList());
	}

	@Override
	public TaxCategory findByCategory(String category) {
		Predicate<TaxCategory> taxByCategory = p -> p.getName().equals(category);
		return TAX_CATEGORIES.values().stream().filter(taxByCategory).findFirst().orElseGet(() -> new TaxCategory(category));
	}

}
