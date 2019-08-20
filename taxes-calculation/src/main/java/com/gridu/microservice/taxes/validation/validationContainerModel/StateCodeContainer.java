package com.gridu.microservice.taxes.validation.validationContainerModel;

import com.gridu.microservice.taxes.validation.annotation.ExistingStateCode;

/**
 * Created by anasimijonovic on 8/19/19.
 */
public class StateCodeContainer {
    @ExistingStateCode
    private String value;

    public StateCodeContainer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
