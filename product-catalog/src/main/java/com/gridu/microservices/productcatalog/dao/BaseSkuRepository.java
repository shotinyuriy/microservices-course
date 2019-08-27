package com.gridu.microservices.productcatalog.dao;

import com.gridu.microservices.productcatalog.model.Sku;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by anasimijonovic on 8/21/19.
 */
public interface BaseSkuRepository extends CrudRepository<Sku, Long> {
}
