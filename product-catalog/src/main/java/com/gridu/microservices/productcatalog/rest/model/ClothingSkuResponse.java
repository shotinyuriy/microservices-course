package com.gridu.microservices.productcatalog.rest.model;

public class ClothingSkuResponse extends SkuResponse {

	private String size;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public ClothingSkuResponse() {
	}

	public ClothingSkuResponse(String skuId, String size) {
		super(skuId);
		this.size = size;
	}
}
