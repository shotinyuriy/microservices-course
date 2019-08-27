package com.gridu.microservices.productcatalog.dao;

import com.gridu.microservices.productcatalog.model.Sku;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by anasimijonovic on 8/21/19.
 */
@Repository
public interface SkuRepository<T extends Sku> extends CrudRepository<T, Long> {
}
