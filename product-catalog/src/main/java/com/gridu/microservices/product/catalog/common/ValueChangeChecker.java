package com.gridu.microservices.product.catalog.common;

import org.springframework.util.StringUtils;

public class ValueChangeChecker {

	public static boolean isValueChanged(String current, String updated) {
		return (StringUtils.isEmpty(current) && !StringUtils.isEmpty(updated))
				|| (!StringUtils.isEmpty(updated) && !current.equals(updated));
	}
	
	public static boolean isValueChanged(Double current, Double updated) {
		return (current == null && updated != null)
				|| (current != null && current != updated);
	}
}
