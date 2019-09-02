package com.gridu.microservice.shoppingcart.model;

/**
 * Created by anasimijonovic on 8/30/19.
 */
public class SkuModel {
    private Long id;
    private ProductModel productModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }
}
