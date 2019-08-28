package com.gridu.microservices.productcatalog.scheduler;

import java.io.Serializable;

/**
 * Created by anasimijonovic on 8/24/19.
 */
public class TaxCategoryModel implements Serializable {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

