package com.gridu.microservices.productcatalog.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Created by anasimijonovic on 8/21/19.
 */
@Entity
@Table(name = "clothing_sku")
public class ClothingSku extends Sku {

    @NotBlank
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String toString() {
        return "clothing sku, size: " + size;
    }
}
