package com.gridu.microservices.product.catalog.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gridu.microservices.product.catalog.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

	ProductCategory findByName(String name);
}
