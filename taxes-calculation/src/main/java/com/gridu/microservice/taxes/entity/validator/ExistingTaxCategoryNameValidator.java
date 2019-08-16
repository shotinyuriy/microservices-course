package com.gridu.microservice.taxes.entity.validator;

import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.validation.GlobalDaoHolder;
import com.gridu.microservice.taxes.validation.annotation.ExistingTaxCategoryName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistingTaxCategoryNameValidator implements ConstraintValidator<ExistingTaxCategoryName, String> {

	private static String ENTITY_NAME = "category.";

	@Override
	public void initialize(ExistingTaxCategoryName existingTaxCategoryName) {
	}

	//TO BE FIXED
	@Override
	public boolean isValid(String taxCategoryName, ConstraintValidatorContext constraintValidatorContext) {

//		List<TaxCategory> foundCategories = GlobalDaoHolder.getTaxCategoryDao()
//				.find(cTaxCategory -> cTaxCategory.getName().equals(taxCategoryName));
//
//		if (foundCategories.size() < 1) {
//			// we are going to add our custom constraint violation
//			// therefore we need to disable the default one to avoid duplicated constraint violations
//			constraintValidatorContext.disableDefaultConstraintViolation();
//			String messageTemplate = ENTITY_NAME + constraintValidatorContext.getDefaultConstraintMessageTemplate();
//			constraintValidatorContext.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
//			return false;
//		}

		return true;
	}
}
