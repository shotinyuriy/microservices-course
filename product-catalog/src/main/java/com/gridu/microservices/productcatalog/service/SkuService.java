package com.gridu.microservices.productcatalog.service;

import com.gridu.microservices.productcatalog.dao.BaseSkuRepository;
import com.gridu.microservices.productcatalog.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by anasimijonovic on 8/21/19.
 */
@Service
public class SkuService {

    @Autowired
    private BaseSkuRepository baseSkuRepository;


    public Sku getSkuById(Long id) {
        return baseSkuRepository.findById(id).orElse(null);
    }

    public Sku saveSku(Sku sku) {
        return baseSkuRepository.save(sku);
    }
}
