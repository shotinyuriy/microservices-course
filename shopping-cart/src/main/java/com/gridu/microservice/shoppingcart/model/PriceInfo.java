package com.gridu.microservice.shoppingcart.model;

/**
 * Created by anasimijonovic on 8/30/19.
 */
public class PriceInfo {
    private double commerceItemsTotal;
    private double taxes;
    private double total;

    public double getCommerceItemsTotal() {
        return commerceItemsTotal;
    }

    public void setCommerceItemsTotal(double commerceItemsTotal) {
        this.commerceItemsTotal = Math.round(commerceItemsTotal * 100) / 100.0;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        // round to 2 decimals
        this.taxes = Math.round(taxes * 100) / 100.0;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = Math.round(total * 100) / 100.0;
    }
}
