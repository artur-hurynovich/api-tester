package com.hurynovich.api_tester.model.controller_response;

import com.hurynovich.api_tester.model.validation.ValidationResult;

import org.springframework.lang.NonNull;

public abstract class AbstractControllerResponse {

    private ValidationResult validationResult;

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(final @NonNull ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

}
