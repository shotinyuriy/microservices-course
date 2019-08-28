package com.gridu.microservices.productcatalog.dao;

import com.gridu.microservices.productcatalog.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by anasimijonovic on 8/20/19.
 */
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

    Optional<ProductCategory> findByName(String name);
}
