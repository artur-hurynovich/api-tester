package com.hurynovich.api_tester.validator.impl.execution_signal_validator;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.state_transition.signal.Signal;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExecutionSignalValidator implements Validator<ExecutionSignal> {

    private final Validator<ExecutionCacheKey> keyValidator;

    public AbstractExecutionSignalValidator(final @NonNull Validator<ExecutionCacheKey> keyValidator) {
        this.keyValidator = keyValidator;
    }

    @Override
    public ValidationResult validate(final @Nullable ExecutionSignal executionSignal) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        if (executionSignal == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionSignal' can't be null");
        } else {
            validateExecutionCacheKey(executionSignal, validationResult);

            validateSignal(executionSignal, validationResult);
        }

        return validationResult;
    }

    private void validateExecutionCacheKey(final @NonNull ExecutionSignal executionSignal,
                                                final @NonNull ValidationResult validationResult) {
        final ExecutionCacheKey executionCacheKey = executionSignal.getExecutionCacheKey();

        if (executionCacheKey == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionSignal.executionCacheKey' can't be null");
        } else {
            final ValidationResult keyValidationResult = keyValidator.validate(executionCacheKey);

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
            validationResult.getDescriptions().add("'executionSignal.signal' can't be null");
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
            validationResult.getDescriptions().add("'executionSignal.signal.signalName' can't be null or empty");
        } else {
            final List<String> validSignalNames = getValidSignalNames(executionSignal);

            if (!validSignalNames.contains(signalName)) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'" + signalName + "' is not a valid 'executionSignal.signal.signalName'");
            }
        }
    }

    protected abstract List<String> getValidSignalNames(@NonNull final ExecutionSignal executionSignal);

}
