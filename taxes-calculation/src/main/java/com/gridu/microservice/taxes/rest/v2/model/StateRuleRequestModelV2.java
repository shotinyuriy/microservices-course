package com.gridu.microservice.taxes.rest.v2.model;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by anasimijonovic on 8/15/19.
 */
public class StateRuleRequestModelV2 {
    private List<@Valid TaxRuleModelV2> taxRules;

    public List<TaxRuleModelV2> getTaxRules() {
        return taxRules;
    }

    public void setTaxRules(List<TaxRuleModelV2> taxRules) {
        this.taxRules = taxRules;
    }
}
