package com.gridu.microservice.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by anasimijonovic on 8/30/19.
 */
public class ShoppingCartModel {
    private String id = UUID.randomUUID().toString();
    private List<CommerceItem> commerceItems = new ArrayList<>();
    private PriceInfo priceInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ShippingAddressModel.ShippingAddress shippingAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CommerceItem> getCommerceItems() {
        return commerceItems;
    }

    public void setCommerceItems(List<CommerceItem> commerceItems) {
        this.commerceItems = commerceItems;
    }

    public PriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public void addCommerceItem(CommerceItem commerceItem) {
        commerceItems.add(commerceItem);
    }

    public ShippingAddressModel.ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressModel.ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}

