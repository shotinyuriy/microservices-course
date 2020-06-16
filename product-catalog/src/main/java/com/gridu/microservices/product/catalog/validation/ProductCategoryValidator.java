package com.gridu.microservices.product.catalog.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.gridu.microservices.product.catalog.model.ProductCategory;
import com.gridu.microservices.product.catalog.service.ProductCategoryService;

public class ProductCategoryValidator implements ConstraintValidator<ValidProductCategory, String> {

	@Autowired
	private ProductCategoryService productCategoryService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		ProductCategory productCategory = productCategoryService.findByName(value);
		if (productCategory == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();;
			return false;
		}
		return true;
	}

}
