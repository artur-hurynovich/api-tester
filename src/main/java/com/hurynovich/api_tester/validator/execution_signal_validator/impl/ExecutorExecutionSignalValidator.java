package com.hurynovich.api_tester.validator.execution_signal_validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.validator.Validator;
import com.hurynovich.api_tester.validator.execution_signal_validator.AbstractExecutionSignalValidator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import static com.hurynovich.api_tester.model.enumeration.ValidationResultType.NON_VALID;

@Service("executorExecutionSignalValidator")
public class ExecutorExecutionSignalValidator extends AbstractExecutionSignalValidator {

    private final ExecutionHelper executionHelper;

    public ExecutorExecutionSignalValidator(final @NonNull Validator<ExecutionStateCacheKey> keyValidator,
                                            final @NonNull ExecutionHelper executionHelper) {
        super(keyValidator);

        this.executionHelper = executionHelper;
    }

    @Override
    protected void processNotNullSignalTypeValidation(final @NonNull ExecutionSignal signal,
                                                      final @NonNull ValidationResult validationResult) {
        final ExecutionStateCacheKey executionStateCacheKey = signal.getKey();
        final ExecutionState executionState = executionHelper.getExecutionState(executionStateCacheKey);

        final ExecutionSignalType signalType = signal.getType();
        if (!executionHelper.resolveValidSignalTypesOnExecution(executionState).contains(signalType)) {
            validationResult.setType(NON_VALID);
            validationResult.getDescriptions().
                    add("Signal '" + signalType + "' is not valid for state '" + executionState + "'");
        }
    }

}
