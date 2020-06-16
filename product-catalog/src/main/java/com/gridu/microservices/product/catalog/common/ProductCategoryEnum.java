package com.gridu.microservices.product.catalog.common;

public enum ProductCategoryEnum {

	BOOKS("books", "undefined"), CLOTHES("clothes", "size"), ELECTRONIC_DEVICES("electronic devices", "color"),
	UNDEFINED("undefined", "undefined");

	private String category;
	private String defaultSkuType;

	private ProductCategoryEnum(String category, String defaultSkuType) {
		this.category = category;
		this.defaultSkuType = defaultSkuType;
	}

	public static ProductCategoryEnum getProductCategory(String category) {
		for (ProductCategoryEnum catEnum : ProductCategoryEnum.values()) {
			if (catEnum.getCategory().equals(category))
				return catEnum;
		}
		return UNDEFINED;
	}

	private String getCategory() {
		return category;
	}

	public String getDefaultSkuType() {
		return defaultSkuType;
	}
}
