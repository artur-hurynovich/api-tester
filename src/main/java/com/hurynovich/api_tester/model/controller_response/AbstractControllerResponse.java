package com.hurynovich.api_tester.model.controller_response;

import com.hurynovich.api_tester.model.validation.ValidationResult;

public abstract class AbstractControllerResponse<T> {

    private T payload;

    private ValidationResult validationResult;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

}
