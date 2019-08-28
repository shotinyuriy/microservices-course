package com.gridu.microservices.product.catalog.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gridu.microservices.product.catalog.model.SkuType;

@Repository
public interface SkuTypeRepository extends CrudRepository<SkuType, Integer> {

	SkuType findByValue(String value);
}
