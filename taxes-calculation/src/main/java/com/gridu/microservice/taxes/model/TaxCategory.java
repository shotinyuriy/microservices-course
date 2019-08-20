package com.gridu.microservice.taxes.model;

import com.gridu.microservice.taxes.validation.ValidationErrorType;
import com.gridu.microservice.taxes.validation.annotation.ExistingTaxCategoryName;
import com.gridu.microservice.taxes.validation.group.TaxCategoryShouldExist;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name="tax_category")
@Entity
public class TaxCategory {

	@Id
	@NotNull(message = ValidationErrorType.MISSING, groups = {TaxCategoryShouldExist.class})
	private Long id;

	@ExistingTaxCategoryName(message = ValidationErrorType.NOT_FOUND, groups = {TaxCategoryShouldExist.class})
	private String name;

	public TaxCategory() {
	}

	public TaxCategory(String name) {
		this.name = name;
	}

	public TaxCategory(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TaxCategory that = (TaxCategory) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		return name != null ? name.equals(that.name) : that.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
