package com.gridu.microservice.shoppingcart.rest.model;

import java.math.BigDecimal;

/**
 * Created by yshotin on 05/09/2019.
 */
public class Price {

	public static double roundPrice(double price) {
		return new BigDecimal(price).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
	}
}
