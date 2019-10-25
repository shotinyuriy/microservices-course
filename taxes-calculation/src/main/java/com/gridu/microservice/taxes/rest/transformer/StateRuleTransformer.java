package com.gridu.microservice.taxes.rest.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxRule;
import com.gridu.microservice.taxes.view.StateRuleViewModel;

@Component
public class StateRuleTransformer {

	public List<StateRuleViewModel> toStateRuleViewModel(List<StateRule> stateRules) {
		List<StateRuleViewModel> viewModels = new ArrayList<StateRuleViewModel>();
		for (StateRule stateRule : stateRules) {
			viewModels.add(toStateRuleViewModel(stateRule));
		}
		return viewModels;
	}

	public StateRuleViewModel toStateRuleViewModel(StateRule stateRule) {
		StateRuleViewModel viewModel = new StateRuleViewModel();
		viewModel.setId(stateRule.getId());
		viewModel.setState(stateRule.getState().getCode());
		viewModel.addRule(getTaxRules(stateRule.getTaxRules()));
		return viewModel;
	}

	public Map<String, Double> getTaxRules(Collection<TaxRule> taxRules) {
		Map<String, Double> viewModelRules = new HashMap<String, Double>();
		for (TaxRule taxRule : taxRules) {
			viewModelRules.put(taxRule.getTaxCategory().getName(), taxRule.getRule());
		}
		return viewModelRules;
	}
}
