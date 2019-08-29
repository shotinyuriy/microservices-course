package com.gridu.microservices.productcatalog.data.validator.impl;

import com.gridu.microservices.productcatalog.data.service.ProductCategoryService;
import com.gridu.microservices.productcatalog.data.validator.annotation.ExistingTaxCategoryName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ExistingTaxCategoryNameValidator implements ConstraintValidator<ExistingTaxCategoryName, String>{

	@Autowired
	private ProductCategoryService productCategoryService;

	public ProductCategoryService getProductCategoryService() {
		return productCategoryService;
	}

	public void setProductCategoryService(ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}

	@Override
	public void initialize(ExistingTaxCategoryName constraintAnnotation) {

	}

	@Override
	public boolean isValid(String productCategoryName, ConstraintValidatorContext constraintValidatorContext) {
		if (getProductCategoryService() == null) { // a crutch for Hibernate Validation during saving to a database
			return true;
		}
		if (getProductCategoryService().getCategories().contains(productCategoryName)) {
			return true;
		}
		return false;
	}
}
