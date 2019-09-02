package com.gridu.microservice.shoppingcart.model;

import javax.validation.constraints.Min;

/**
 * Created by anasimijonovic on 8/30/19.
 */
public class ItemRequestModel {
    private Long skuId;
    @Min(1)
    private int quantity;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
