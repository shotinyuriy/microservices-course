package com.gridu.microservices.productcatalog.rest.model;

import com.gridu.microservices.productcatalog.validation.annotation.UniqueProductName;

/**
 * Created by anasimijonovic on 8/22/19.
 */
public class ProductName {
    @UniqueProductName
    private String name;

    public ProductName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
