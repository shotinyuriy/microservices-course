package com.gridu.microservices.productcatalog.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gridu.microservices.productcatalog.model.Product;

@Repository
public interface DataRepository extends CrudRepository<Product, Long> {

}
