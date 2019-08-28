package com.gridu.microservices.productcatalog.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Created by anasimijonovic on 8/21/19.
 */
@Entity
@Table(name = "electronic_device_sku")
public class ElectronicDeviceSku extends Sku {

    @NotBlank
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return "electronic device sku, size: " + color;
    }
}
