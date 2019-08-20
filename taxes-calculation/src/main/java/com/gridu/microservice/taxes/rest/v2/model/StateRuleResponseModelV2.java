package com.gridu.microservice.taxes.rest.v2.model;

import java.util.List;

/**
 * Created by anasimijonovic on 8/15/19.
 */
public class StateRuleResponseModelV2 {
    private Long id;
    private String stateCode;
    private List<TaxRuleModelV2> taxRules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public List<TaxRuleModelV2> getTaxRules() {
        return taxRules;
    }

    public void setTaxRules(List<TaxRuleModelV2> taxRules) {
        this.taxRules = taxRules;
    }
}
