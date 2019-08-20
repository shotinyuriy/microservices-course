package com.gridu.microservice.taxes.rest.v2;

import com.gridu.microservice.taxes.exception.CustomConstraintViolationException;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.model.TaxRule;
import com.gridu.microservice.taxes.rest.transformer.ValidationResultTransformer;
import com.gridu.microservice.taxes.rest.v2.model.StateRuleRequestModelV2;
import com.gridu.microservice.taxes.rest.v2.model.StateRuleResponseModelV2;
import com.gridu.microservice.taxes.rest.v2.model.TaxRuleModelV2;
import com.gridu.microservice.taxes.rest.v2.transformer.StateRuleTransformerV2;
import com.gridu.microservice.taxes.service.StateRuleService;
import com.gridu.microservice.taxes.service.StateService;
import com.gridu.microservice.taxes.service.TaxCategoryService;
import com.gridu.microservice.taxes.validation.ValidationResult;
import com.gridu.microservice.taxes.validation.ValidationService;
import com.gridu.microservice.taxes.validation.group.StateCodeValidationGroup;
import com.gridu.microservice.taxes.validation.group.TaxCategoryShouldExist;
import com.gridu.microservice.taxes.validation.validationContainerModel.StateCodeContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

/**
 * Created by anasimijonovic on 8/15/19.
 */
@RestController
@RequestMapping("/taxes")
public class TaxesCalculationRestResourceV2 {

    @Autowired
    private StateRuleTransformerV2 stateRuleTransformer;

    @Autowired
    private StateRuleService stateRuleService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ValidationResultTransformer validationResultTransformer;

    @Autowired
    private StateService stateService;

    @Autowired
    private TaxCategoryService taxCategoryService;

    @GetMapping(value = "/stateRules/v2", produces = "application/json")
    public ResponseEntity<List<StateRuleResponseModelV2>> getStateRules() {
        return ResponseEntity.ok(stateRuleTransformer.toStateRuleResponseModel(stateRuleService.getAll()));
    }

    @GetMapping(value = "/stateRules/v2/{stateCode}", produces = "application/json")
    public ResponseEntity<?> getStateRule(@PathVariable String stateCode) {
        StateCodeContainer stateCodeContainer = new StateCodeContainer(stateCode);

        List<ValidationResult> validationResults = validationService.validate(stateCodeContainer);
        if (!validationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(validationResults),
                    HttpStatus.NOT_FOUND);
        }

        StateRule stateRule = stateRuleService.getStateRule(stateCode);

        return ResponseEntity.ok(stateRuleTransformer.toStateRuleResponseModel(stateRule));
    }

    @PostMapping(value = "/stateRules/v2/{stateCode}", produces = "application/json")
    public ResponseEntity<?> addStateRule(@PathVariable String stateCode, @RequestBody StateRuleRequestModelV2 rules) {
        List<ValidationResult> requestDataValidationResults = validationService.validate(rules, TaxCategoryShouldExist.class);
        if (!requestDataValidationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(requestDataValidationResults),
                    HttpStatus.BAD_REQUEST);
        }

        StateRule stateRule = provideStateRule(stateCode, rules);

        List<ValidationResult> validationResults = validationService.validate(stateRule, Default.class,
                StateCodeValidationGroup.class, TaxCategoryShouldExist.class);
        if (!validationResults.isEmpty()) {
            throw new CustomConstraintViolationException("Constraint violation.",
                    validationResultTransformer.provideValidationErrorResponse(validationResults),
                    HttpStatus.BAD_REQUEST);
        }
        StateRule saveStateRule = stateRuleService.saveStateRule(stateRule);

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body("/stateRules/v2/" + saveStateRule.getState().getCode());
    }


    private StateRule provideStateRule(String stateCode, StateRuleRequestModelV2 rules) {
        StateRule stateRule = stateRuleService.getStateRule(stateCode);
        if (stateRule == null || stateRule.getId() == null) {
            stateRule = new StateRule(stateService.getStateDao().findByCode(stateCode));
        }
        for (TaxRuleModelV2 taxRuleModelV2 : rules.getTaxRules()) {
            TaxCategory category = taxCategoryService.findByCategory(taxRuleModelV2.getCategoryName());
            stateRule.addTaxRule(new TaxRule(category, taxRuleModelV2.getTaxRule()));
        }
        return stateRule;
    }
}
