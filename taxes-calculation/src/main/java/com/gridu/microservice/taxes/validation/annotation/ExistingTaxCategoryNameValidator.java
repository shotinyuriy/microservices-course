package com.gridu.microservice.taxes.validation.annotation;


import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistingTaxCategoryNameValidator implements ConstraintValidator<ExistingTaxCategoryName, String> {

	@Override
	public void initialize(ExistingTaxCategoryName existingTaxCategoryName) {
	}

	@Override
	public boolean isValid(String taxCategoryName, ConstraintValidatorContext constraintValidatorContext) {

		List<TaxCategory> foundCategories = GlobalDaoHolder.getTaxCategoryDao()
			.find(cTaxCategory -> cTaxCategory.getName().equals(taxCategoryName));

		if (foundCategories.size() < 1) {
			// we are going to add our custom constraint violation
			// therefore we need to disable the default one to avoid duplicated constraint violations
			constraintValidatorContext.disableDefaultConstraintViolation();

			constraintValidatorContext
				.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
				.addConstraintViolation();
			return false;
		}

		return true;
	}
}
