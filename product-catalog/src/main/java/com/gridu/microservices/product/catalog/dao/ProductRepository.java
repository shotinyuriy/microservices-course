package com.gridu.microservices.product.catalog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gridu.microservices.product.catalog.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	@Query(value = "select * from product p join product_catalog pc "
			+ "on p.prod_cat_id = pc.id "
			+ "where p.name = :name and pc.name = :category",
			nativeQuery = true)
	Product findUniqueProduct(@Param("name") String name, @Param("category") String category);
}
