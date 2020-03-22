package com.hurynovich.api_tester.validator.execution_signal_validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.state_transition.signal.SignalName;
import com.hurynovich.api_tester.validator.Validator;
import com.hurynovich.api_tester.validator.execution_signal_validator.AbstractExecutionSignalValidator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("controllerExecutionSignalValidator")
public class ControllerExecutionSignalValidator extends AbstractExecutionSignalValidator {

    private final ExecutionHelper executionHelper;

    public ControllerExecutionSignalValidator(final @NonNull Validator<ExecutionStateCacheKey> keyValidator,
                                              final @NonNull ExecutionHelper executionHelper) {
        super(keyValidator);

        this.executionHelper = executionHelper;
    }

    @Override
    public ValidationResult validate(final @NonNull ExecutionSignal executionSignal) {
        final ValidationResult validationResult = super.validate(executionSignal);

        final ExecutionStateCacheKey executionStateCacheKey = executionSignal.getExecutionStateCacheKey();

        final ExecutionState executionState = executionHelper.getExecutionState(executionStateCacheKey);

        if (executionState == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("Execution was not initialized");
        }

        return validationResult;
    }

    @Override
    protected List<String> getValidSignalNames(final ExecutionSignal executionSignal) {
        final ExecutionStateCacheKey executionStateCacheKey = executionSignal.getExecutionStateCacheKey();

        final ExecutionState executionState = executionHelper.getExecutionState(executionStateCacheKey);

        if (executionState != null) {
            return executionHelper.resolveValidSignalNamesOnInit(executionState);
        } else {
            return Collections.singletonList(SignalName.RUN);
        }
    }

}
