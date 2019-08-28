package com.gridu.microservices.product.catalog.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gridu.microservices.product.catalog.common.ValueChangeChecker;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "prod_cat_id")
	private ProductCategory category;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<ProductSku> childSkus = new ArrayList<ProductSku>();

	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;

	private String name;
	private Double price;

	public ProductCategory getCategory() {
		return category;
	}

	public Collection<ProductSku> getChildSkus() {
		return childSkus;
	}

	public Currency getCurrency() {
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
	
	public void setCategory(ProductCategory category) {
		if (this.category == null || !this.category.equals(category)) {
			this.category = category;
		}
	}

	public void setChildSkus(Collection<ProductSku> childSkus) {
		this.childSkus = childSkus;
	}

	public void addChildSkus(ProductSku sku) {
		sku.setProduct(this);
		this.childSkus.add(sku);
	}

	public void setCurrency(Currency currency) {
		if (this.currency == null || !this.currency.equals(currency)) {
			this.currency = currency;
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		if (ValueChangeChecker.isValueChanged(this.name, name)) {
			this.name = name;
		}
	}

	public void setPrice(Double price) {
		if (ValueChangeChecker.isValueChanged(this.price, price)) {
			this.price = price;
		}
	}
}
