package com.hurynovich.api_tester.validator.execution_signal_validator;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.state_transition.signal.Signal;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExecutionSignalValidator implements Validator<ExecutionSignal> {

    private final Validator<ExecutionStateCacheKey> keyValidator;

    public AbstractExecutionSignalValidator(final @NonNull Validator<ExecutionStateCacheKey> keyValidator) {
        this.keyValidator = keyValidator;
    }

    @Override
    public ValidationResult validate(final @NonNull ExecutionSignal executionSignal) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        validateExecutionStateCacheKey(executionSignal, validationResult);

        validateSignal(executionSignal, validationResult);

        return validationResult;
    }

    private void validateExecutionStateCacheKey(final @NonNull ExecutionSignal executionSignal,
                                                final @NonNull ValidationResult validationResult) {
        final ExecutionStateCacheKey executionStateCacheKey = executionSignal.getExecutionStateCacheKey();

        if (executionStateCacheKey == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionStateCacheKey' can't be null");
        } else {
            final ValidationResult keyValidationResult = keyValidator.validate(executionStateCacheKey);

            final ValidationResultType keyValidationResultType = keyValidationResult.getType();
            if (keyValidationResultType == ValidationResultType.NON_VALID) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().addAll(keyValidationResult.getDescriptions());
            }
        }
    }

    private void validateSignal(final @NonNull ExecutionSignal executionSignal,
                                final @NonNull ValidationResult validationResult) {
        final Signal signal = executionSignal.getSignal();

        if (signal == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'signal' can't be null");
        } else {
            processNotNullSignalValidation(executionSignal, validationResult);
        }
    }

    private void processNotNullSignalValidation(final @NonNull ExecutionSignal executionSignal,
                                                final @NonNull ValidationResult validationResult) {
        final Signal signal = executionSignal.getSignal();

        final String signalName = signal.getName();

        if (signalName == null || signalName.isEmpty()) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'signalName' can't be null or empty");
        } else {
            final List<String> validSignalNames = getValidSignalNames(executionSignal);

            if (!validSignalNames.contains(signalName)) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'" + signalName + "' is not a valid signal name");
            }
        }
    }

    protected abstract List<String> getValidSignalNames(@NonNull final ExecutionSignal executionSignal);

}
