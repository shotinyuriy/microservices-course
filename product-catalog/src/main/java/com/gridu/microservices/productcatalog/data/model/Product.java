package com.gridu.microservices.productcatalog.data.model;

import com.gridu.microservice.rest.validation.ValidationErrorType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="product")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@NotNull(message = ValidationErrorType.MISSING)
	@NotEmpty(message = ValidationErrorType.MISSING)
	private String name;

	@NotNull(message = ValidationErrorType.MISSING)
	@NotEmpty(message = ValidationErrorType.MISSING)
	@Pattern(regexp = "(books)|(clothing)|(electronic devices)", message = ValidationErrorType.INVALID)
	private String category;

	@NotNull(message = ValidationErrorType.MISSING)
	@Min(value = 0, message = ValidationErrorType.TOO_SMALL)
	private Double price;

	@Cascade(CascadeType.ALL)
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private List<Sku> childSkus;

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Sku> getChildSkus() {
		return childSkus;
	}

	public void setChildSkus(List<Sku> childSkus) {
		this.childSkus = childSkus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Product product = (Product) o;

		return id != null ? id.equals(product.id) : product.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}


}
