package com.gridu.microservices.product.catalog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_sku")
public class ProductSku {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "sku_type_id")
	private SkuType skuType;

	private String skuValue;

	public Long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public SkuType getSkuType() {
		return skuType;
	}

	public String getSkuValue() {
		return skuValue;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setSkuType(SkuType skuType) {
		this.skuType = skuType;
	}

	public void setSkuValue(String skuValue) {
		this.skuValue = skuValue;
	}
}
