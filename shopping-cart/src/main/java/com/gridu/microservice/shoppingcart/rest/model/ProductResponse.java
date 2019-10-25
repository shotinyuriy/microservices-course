package com.gridu.microservice.shoppingcart.rest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

@RedisHash("products")
public class ProductResponse implements Serializable {

	@Id
	private String id;
	private @Indexed String name;
	private @Indexed String category;
	private @Indexed double price;
	private List<SkuResponse> childSkus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<SkuResponse> getChildSkus() {
		return childSkus;
	}

	public void setChildSkus(List<SkuResponse> childSkus) {
		this.childSkus = childSkus;
	}
}
