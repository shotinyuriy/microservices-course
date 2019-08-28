package com.gridu.microservices.productcatalog.rest.model;

import com.gridu.microservices.productcatalog.validation.annotation.ExistingSkuId;

/**
 * Created by anasimijonovic on 8/27/19.
 */
public class SkuId {
    @ExistingSkuId
    private Long id;

    public SkuId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
