package com.gridu.microservice.shoppingcart.model;

import com.gridu.microservice.shoppingcart.service.TaxesCalculationItemsModel;

import java.util.UUID;

/**
 * Created by anasimijonovic on 8/30/19.
 */
public class CommerceItem {
    private String id = UUID.randomUUID().toString();
    private Long skuId;
    private Long productId;
    private int quantity;
    private Double price;
    private Double totalPrice;
    private TaxesCalculationItemsModel.TaxesModel taxes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculateAndSetTotalPrice() {
        this.totalPrice = this.quantity * this.price;
    }

    public TaxesCalculationItemsModel.TaxesModel getTaxes() {
        return taxes;
    }

    public void setTaxes(TaxesCalculationItemsModel.TaxesModel taxes) {
        this.taxes = taxes;
    }
}
