package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;

public class ValidatorTestHelper {

    private ValidatorTestHelper() {

    }

    public static ValidationResult buildValidValidationResult() {
        final ValidationResult validationResult = new ValidationResult();

        validationResult.setType(ValidationResultType.VALID);

        return validationResult;
    }

}
