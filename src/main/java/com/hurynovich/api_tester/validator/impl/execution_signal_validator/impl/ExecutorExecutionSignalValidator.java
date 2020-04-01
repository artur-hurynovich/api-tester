package com.hurynovich.api_tester.validator.impl.execution_signal_validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.validator.Validator;
import com.hurynovich.api_tester.validator.impl.execution_signal_validator.AbstractExecutionSignalValidator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("executorExecutionSignalValidator")
public class ExecutorExecutionSignalValidator extends AbstractExecutionSignalValidator {

    private final ExecutionHelper executionHelper;

    public ExecutorExecutionSignalValidator(final @NonNull Validator<ExecutionCacheKey> keyValidator,
                                            final @NonNull ExecutionHelper executionHelper) {
        super(keyValidator);

        this.executionHelper = executionHelper;
    }

    @Override
    protected void validateSignalName(final @NonNull ExecutionSignal executionSignal,
                                      final @NonNull ValidationResult validationResult) {
        final ExecutionCacheKey executionCacheKey = executionSignal.getExecutionCacheKey();

        final ExecutionState executionState = executionHelper.getExecutionState(executionCacheKey);

        final List<String> validSignalNames = executionHelper.resolveValidSignalNamesOnExecution(executionState);

        final String signalName = executionSignal.getSignal().getName();

        if (!validSignalNames.contains(signalName)) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'" + signalName + "' is not a valid 'executionSignal.signal.signalName'");
        }
    }

}
