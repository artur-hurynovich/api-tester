package com.hurynovich.api_tester.model.controller_response;

import com.hurynovich.api_tester.model.validation.ValidationResult;

import org.springframework.lang.NonNull;

public class ControllerResponse<T> {

    private T payload;

    private ValidationResult validationResult;

    public T getPayload() {
        return payload;
    }

    public void setPayload(final @NonNull T payload) {
        this.payload = payload;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(final @NonNull ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

}
