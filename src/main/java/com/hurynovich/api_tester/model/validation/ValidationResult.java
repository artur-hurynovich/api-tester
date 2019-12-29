package com.hurynovich.api_tester.model.validation;

import com.hurynovich.api_tester.model.enumeration.ValidationResultType;

import java.util.List;

public class ValidationResult {

    private ValidationResultType type;

    private List<String> descriptions;

    public ValidationResultType getType() {
        return type;
    }

    public void setType(final ValidationResultType type) {
        this.type = type;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(final List<String> descriptions) {
        this.descriptions = descriptions;
    }

}
