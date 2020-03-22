package com.hurynovich.api_tester.validator.execution_signal_validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.validator.Validator;
import com.hurynovich.api_tester.validator.execution_signal_validator.AbstractExecutionSignalValidator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("executorExecutionSignalValidator")
public class ExecutorExecutionSignalValidator extends AbstractExecutionSignalValidator {

    private final ExecutionHelper executionHelper;

    public ExecutorExecutionSignalValidator(final @NonNull Validator<ExecutionStateCacheKey> keyValidator,
                                            final @NonNull ExecutionHelper executionHelper) {
        super(keyValidator);

        this.executionHelper = executionHelper;
    }

    @Override
    protected List<String> getValidSignalNames(final ExecutionSignal executionSignal) {
        final ExecutionStateCacheKey executionStateCacheKey = executionSignal.getExecutionStateCacheKey();

        final ExecutionState executionState = executionHelper.getExecutionState(executionStateCacheKey);

        return executionHelper.resolveValidSignalNamesOnExecution(executionState);
    }

}
