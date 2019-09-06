package com.gridu.microservices.productcatalog.data.model;

import com.gridu.microservice.rest.validation.ValidationErrorType;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "electronic_device_sku")
public class ElectronicDeviceSku extends Sku {

	@NotNull(message = ValidationErrorType.MISSING)
	@NotEmpty(message = ValidationErrorType.MISSING)
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
