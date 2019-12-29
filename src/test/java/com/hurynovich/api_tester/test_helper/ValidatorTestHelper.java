package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;

import java.util.ArrayList;

public class ValidatorTestHelper {

    private ValidatorTestHelper() {

    }

    public static ValidationResult buildValidValidationResult() {
        final ValidationResult validationResult = new ValidationResult();

        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        return validationResult;
    }

}
