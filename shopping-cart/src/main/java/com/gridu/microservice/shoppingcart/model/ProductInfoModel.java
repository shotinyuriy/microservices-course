package com.gridu.microservice.shoppingcart.model;

/**
 * Created by anasimijonovic on 8/30/19.
 */
public class ProductInfoModel {
    private Long id;
    private Double price;
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
