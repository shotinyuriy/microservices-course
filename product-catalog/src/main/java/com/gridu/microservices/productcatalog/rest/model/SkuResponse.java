package com.gridu.microservices.productcatalog.rest.model;

public class SkuResponse {

	private String skuId;

	public SkuResponse() {
	}

	public SkuResponse(String skuId) {
		this.skuId = skuId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
}
