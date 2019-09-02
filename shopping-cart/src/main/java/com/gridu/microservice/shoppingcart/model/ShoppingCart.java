package com.gridu.microservice.shoppingcart.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by anasimijonovic on 8/30/19.
 */
public class ShoppingCart {
    private String id = UUID.randomUUID().toString();
    private List<CommerceItem> commerceItems = new ArrayList<>();
    private PriceInfo priceInfo = new PriceInfo();
    private ShippingAddressModel shippingAddress;

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

    public ShippingAddressModel getShippingAddress() {
        return shippingAddress;
    }

    public boolean hasShippingAddress() {
        return getShippingAddress() != null
                && getShippingAddress().getShippingAddress() != null
                && getShippingAddress().getShippingAddress().getState() != null;
    }

    public void setShippingAddress(ShippingAddressModel shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void calculateAndSetPriceInfo() {
        PriceInfo priceInfo = new PriceInfo();

        // setting the commerce items total
        commerceItems.forEach(commerceItem -> priceInfo.setCommerceItemsTotal(priceInfo.getCommerceItemsTotal() + commerceItem.getTotalPrice()));

        // setting total taxes
        if (shippingAddress != null) {
            Double taxes = commerceItems.stream()
                    .map(commerceItem -> Double.valueOf(commerceItem.getTaxes().getStateTax()))
                    .collect(Collectors.summingDouble(Double::doubleValue));

            priceInfo.setTaxes(taxes);
        }

        // setting the total price
        priceInfo.setTotal(priceInfo.getTaxes() + priceInfo.getCommerceItemsTotal());

        setPriceInfo(priceInfo);
    }
}
