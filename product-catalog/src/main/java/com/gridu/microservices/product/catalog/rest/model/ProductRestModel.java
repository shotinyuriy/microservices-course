package com.gridu.microservices.product.catalog.rest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gridu.microservices.product.catalog.validation.ValidProductCategory;
import com.gridu.microservices.product.catalog.validation.ValidationConstants;

public class ProductRestModel {

	public static class ProductRestModelBuilder {

		private String category;
		private String currency;
		private final String DEFAULT_STRING_VALUE = "";
		private Long id;
		private String name;
		private Double price;
		private List<SkuModel> skus;

		public ProductRestModelBuilder addProductSku(SkuModel sku) {
			if (this.skus == null) {
				skus = new ArrayList<SkuModel>();
			}
			skus.add(sku);
			return this;
		}

		public ProductRestModel build() {
			return new ProductRestModel(id, name, category, price, currency, skus);
		}

		public ProductRestModelBuilder setCategory(String category) {
			this.category = StringUtils.isEmpty(category) ? DEFAULT_STRING_VALUE : category;
			return this;
		}

		public ProductRestModelBuilder setCurrency(String currency) {
			this.currency = StringUtils.isEmpty(currency) ? DEFAULT_STRING_VALUE : currency;
			return this;
		}

		public ProductRestModelBuilder setId(Long id) {
			this.id = id == null ? -1 : id;
			return this;
		}

		public ProductRestModelBuilder setName(String name) {
			this.name = StringUtils.isEmpty(name) ? DEFAULT_STRING_VALUE : name;
			return this;
		}

		public ProductRestModelBuilder setPrice(Double price) {
			this.price = price == null ? -1 : price;
			return this;
		}

		public ProductRestModelBuilder setProductSkus(List<SkuModel> skus) {
			this.skus = skus;
			return this;
		}
	}

	public static class SkuModel {
		private Long id;

		private Map<String, String> skus;

		public Long getId() {
			return id;
		}

		public Map<String, String> getSkus() {
			return skus;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setSkus(Map<String, String> skus) {
			this.skus = skus;
		}

	}

	@NotEmpty(message = ValidationConstants.EMPTY)
	@ValidProductCategory(message = ValidationConstants.INVALID)
	private String category;

	@NotEmpty(message = ValidationConstants.EMPTY)
	private String currency;

	private Long id;

	@NotEmpty(message = ValidationConstants.EMPTY)
	private String name;

	@Min(value = (long) 10, message = ValidationConstants.TOO_SMALL)
	@NotNull(message = ValidationConstants.EMPTY)
	private Double price;

	private List<SkuModel> skus;

	private ProductRestModel() {
	}

	private ProductRestModel(Long id, String name, String category, Double price, String currency,
			List<SkuModel> skus) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.currency = currency;
		this.skus = skus;
	}

	public String getCategory() {
		return category;
	}

	public String getCurrency() {
		return currency;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(value = "childSkus")
	public List<SkuModel> getSkus() {
		return skus;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
