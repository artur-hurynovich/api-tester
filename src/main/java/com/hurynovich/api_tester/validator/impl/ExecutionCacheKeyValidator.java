package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class ExecutionCacheKeyValidator implements Validator<ExecutionCacheKey> {

    private static final String UUID_VALIDATION_REGEX =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Override
    public ValidationResult validate(final @Nullable ExecutionCacheKey executionCacheKey) {
        final ValidationResult validationResult = ValidationResult.createValidResult();

        if (executionCacheKey == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionCacheKey' can't be null");
        } else {
            validateExecutionKey(executionCacheKey, validationResult);
        }

        return validationResult;
    }

    private void validateExecutionKey(final @NonNull ExecutionCacheKey executionCacheKey,
                                      final @NonNull ValidationResult validationResult) {
        final String executionKey = executionCacheKey.getExecutionKey();

        if (executionKey == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionCacheKey.executionKey' can't be null");
        } else if (!executionKey.matches(UUID_VALIDATION_REGEX)) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'" + executionKey + "' is not a valid 'executionCacheKey.executionKey'");
        }
    }


}
