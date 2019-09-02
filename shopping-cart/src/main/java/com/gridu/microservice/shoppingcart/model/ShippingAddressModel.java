package com.gridu.microservice.shoppingcart.model;

/**
 * Created by anasimijonovic on 8/30/19.
 */
public class ShippingAddressModel {
    private ShippingAddress shippingAddress;

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public static class ShippingAddress {
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
