package com.hurynovich.api_tester.validator.execution_state_cache_key_validator;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExecutionStateCacheKeyValidator implements Validator<ExecutionStateCacheKey> {

    private static final String UUID_VALIDATION_REGEX =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Override
    public ValidationResult validate(final @NonNull ExecutionStateCacheKey key) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        validateExecutionKey(key, validationResult);

        return validationResult;
    }

    private void validateExecutionKey(final @NonNull ExecutionStateCacheKey key,
                                      final @NonNull ValidationResult validationResult) {
        final String executionKey = key.getExecutionKey();

        if (executionKey == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionKey' can't be null");
        } else if (!executionKey.matches(UUID_VALIDATION_REGEX)) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionKey' is not valid");
        }
    }


}
