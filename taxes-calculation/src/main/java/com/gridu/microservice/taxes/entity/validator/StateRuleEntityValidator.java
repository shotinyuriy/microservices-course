package com.gridu.microservice.taxes.entity.validator;

import org.springframework.stereotype.Component;

import com.gridu.microservice.taxes.model.StateRule;

@Component
public class StateRuleEntityValidator implements EntityValidator<StateRule> {

	@Override
	public boolean isValid(StateRule entity) {
		
		boolean valid = false;
		if(entity != null && entity.getState() != null && entity.getState().getCode() != null && !entity.getTaxRules().isEmpty()) {
			valid = true;
		}
		return valid;
	}
}
