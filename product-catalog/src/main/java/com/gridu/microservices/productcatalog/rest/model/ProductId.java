package com.gridu.microservices.productcatalog.rest.model;

import com.gridu.microservices.productcatalog.validation.annotation.ExistingProductId;

/**
 * Created by anasimijonovic on 8/21/19.
 */
public class ProductId {
    @ExistingProductId
    private Long productId;

    public ProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
