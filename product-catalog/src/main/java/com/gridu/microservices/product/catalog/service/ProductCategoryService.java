package com.gridu.microservices.product.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.gridu.microservices.product.catalog.dao.ProductCategoryRepository;
import com.gridu.microservices.product.catalog.model.ProductCategory;

@Service
public class ProductCategoryService extends GenericRepositoryService<ProductCategory, Long> {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	protected CrudRepository<ProductCategory, Long> getRepository() {
		return productCategoryRepository;
	}

	public ProductCategory findByName(String name) {
		return productCategoryRepository.findByName(name);
	}
}
