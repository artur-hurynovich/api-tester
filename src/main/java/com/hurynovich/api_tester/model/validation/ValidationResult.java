package com.hurynovich.api_tester.model.validation;

import com.hurynovich.api_tester.model.enumeration.ValidationResultType;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private ValidationResultType type;

    private List<String> descriptions = new ArrayList<>();

    public static ValidationResult createValidResult() {
        final ValidationResult validationResult = new ValidationResult();

        validationResult.setType(ValidationResultType.VALID);

        return validationResult;
    }

    public ValidationResultType getType() {
        return type;
    }

    public void setType(final ValidationResultType type) {
        this.type = type;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

}
