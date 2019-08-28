package com.gridu.microservices.productcatalog.data.model;

import com.gridu.microservice.rest.validation.ValidationErrorType;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="clothing_sku")
public class ClothingSku extends Sku {

	@NotNull(message = ValidationErrorType.MISSING)
	@NotEmpty(message = ValidationErrorType.MISSING)
	private String size;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
