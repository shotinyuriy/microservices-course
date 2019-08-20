package com.gridu.microservice.taxes.rest.v2.transformer;

import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxRule;
import com.gridu.microservice.taxes.rest.v2.model.TaxRuleModelV2;
import com.gridu.microservice.taxes.rest.v2.model.StateRuleResponseModelV2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anasimijonovic on 8/15/19.
 */
@Component
public class StateRuleTransformerV2 {
    public List<StateRuleResponseModelV2> toStateRuleResponseModel(List<StateRule> stateRules) {
        List<StateRuleResponseModelV2> responseModel = new ArrayList<>();
        stateRules.forEach(stateRule -> responseModel.add(toStateRuleResponseModel(stateRule)));

        return responseModel;
    }

    public StateRuleResponseModelV2 toStateRuleResponseModel(StateRule stateRule) {
        StateRuleResponseModelV2 responseModel = new StateRuleResponseModelV2();
        responseModel.setId(stateRule.getId());
        responseModel.setStateCode(stateRule.getState().getCode());
        responseModel.setTaxRules(toTaxRulesResponseModel(stateRule.getTaxRules()));

        return responseModel;
    }

    public List<TaxRuleModelV2> toTaxRulesResponseModel(List<TaxRule> taxRules) {
        List<TaxRuleModelV2> responseModel = new ArrayList<>();
        taxRules.forEach(taxRule -> responseModel.add(new TaxRuleModelV2(taxRule.getTaxCategory().getName(), taxRule.getRule())));

        return responseModel;
    }
}
