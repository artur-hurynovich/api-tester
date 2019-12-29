package com.hurynovich.api_tester.validator.execution_signal_validator;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public abstract class AbstractExecutionSignalValidator implements Validator<ExecutionSignal> {

    private final Validator<ExecutionStateCacheKey> keyValidator;

    public AbstractExecutionSignalValidator(final @NonNull Validator<ExecutionStateCacheKey> keyValidator) {
        this.keyValidator = keyValidator;
    }

    @Override
    public ValidationResult validate(final @NonNull ExecutionSignal signal) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        validateExecutionStateCacheKey(signal, validationResult);

        validateExecutionSignalType(signal, validationResult);

        return validationResult;
    }

    private void validateExecutionStateCacheKey(final @NonNull ExecutionSignal signal,
                                                final @NonNull ValidationResult validationResult) {
        final ExecutionStateCacheKey key = signal.getKey();

        if (key == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'key' can't be null");
        } else {
            final ValidationResult keyValidationResult = keyValidator.validate(key);

            final ValidationResultType keyValidationResultType = keyValidationResult.getType();
            if (keyValidationResultType == ValidationResultType.NON_VALID) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().addAll(keyValidationResult.getDescriptions());
            }
        }
    }

    private void validateExecutionSignalType(final @NonNull ExecutionSignal signal,
                                             final @NonNull ValidationResult validationResult) {
        final ExecutionSignalType type = signal.getType();

        if (type == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'type' can't be null");
        } else {
            processNotNullSignalTypeValidation(signal, validationResult);
        }
    }

    protected abstract void processNotNullSignalTypeValidation(final @NonNull ExecutionSignal signal,
                                                               final @NonNull ValidationResult validationResult);

}
