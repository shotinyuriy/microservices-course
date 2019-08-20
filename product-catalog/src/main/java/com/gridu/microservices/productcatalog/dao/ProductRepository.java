package com.gridu.microservices.productcatalog.dao;

import com.gridu.microservices.productcatalog.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anasimijonovic on 8/20/19.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByName(String name);
}
