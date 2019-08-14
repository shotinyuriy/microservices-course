package com.gridu.microservice.taxes.service;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gridu.microservice.taxes.model.TaxesCalculationItemsModel;
import com.gridu.microservice.taxes.model.TaxesCalculationItemsModel.TaxCalculationItemModel;
import com.gridu.microservice.taxes.model.TaxesCalculationItemsModel.TaxesModel;

@Service
public class TaxCalculatorService {

	private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
	
	@Autowired
	private StateRuleService stateRuleService;

	public Double calculateTaxPrice(String stateCode, String category, Double price) {
		long stateRuleId = getStateRuleService().getStateRule(stateCode).getId();
		return price * getStateRuleService().getStateRule(stateRuleId).getTax(category);
	}

	public void setStateRuleService(StateRuleService stateRuleService) {
		this.stateRuleService = stateRuleService;
	}
	
	public void updateTaxCalcItemsModelWithTax(TaxesCalculationItemsModel model) {
		for (TaxCalculationItemModel taxCalcItem : model.getItems()) {
			String tax = DECIMAL_FORMAT
					.format(calculateTaxPrice(model.getStateCode(), taxCalcItem.getCategory(), taxCalcItem.getPrice()));
			taxCalcItem.setTaxes(new TaxesModel(tax));
		}
	}

	private StateRuleService getStateRuleService() {
		return stateRuleService;
	}
}
