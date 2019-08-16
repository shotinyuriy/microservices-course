package com.gridu.microservice.taxes.rest.v2.model;

/**
 * Created by anasimijonovic on 8/15/19.
 */
public class TaxRuleModelV2 {
    private String categoryName;
    private Double taxRule;

    public TaxRuleModelV2() {
    }

    public TaxRuleModelV2(String categoryName, Double taxRule) {
        this.categoryName = categoryName;
        this.taxRule = taxRule;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getTaxRule() {
        return taxRule;
    }

    public void setTaxRule(Double taxRule) {
        this.taxRule = taxRule;
    }
}
