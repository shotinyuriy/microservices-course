package com.gridu.microservice.taxes.validation.annotation;


import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

public class ExistingTaxCategoryMapValidator implements ConstraintValidator<ExistingTaxCategoryMap, Map<String, ?>> {

	@Override
	public void initialize(ExistingTaxCategoryMap existingTaxCategoryMap) {
	}

	@Override
	public boolean isValid(Map<String, ?> rules, ConstraintValidatorContext constraintValidatorContext) {

		boolean valid = true;
		for (String taxCategoryName : rules.keySet()) {
			List<TaxCategory> foundCategories = GlobalDaoHolder.getTaxCategoryDao()
				.find(cTaxCategory -> cTaxCategory.getName().equals(taxCategoryName));

			if (foundCategories.size() < 1) {
				// we are going to add our custom constraint violation
				// therefore we need to disable the default one to avoid duplicated constraint violations
				constraintValidatorContext.disableDefaultConstraintViolation();

				constraintValidatorContext
					.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
					.addPropertyNode(taxCategoryName) // we need to put the invalid tax category name some where,
														// ideally it would be a value, but it is mutable via this constraintValidatorContext
					.addConstraintViolation();
				valid = false;
			}
		};

		return valid;
	}
}
