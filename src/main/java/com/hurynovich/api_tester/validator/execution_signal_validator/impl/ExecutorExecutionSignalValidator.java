package com.hurynovich.api_tester.validator.execution_signal_validator.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.execution_transition_container.ExecutionTransitionContainer;
import com.hurynovich.api_tester.validator.execution_signal_validator.AbstractExecutionSignalValidator;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import static com.hurynovich.api_tester.model.enumeration.ValidationResultType.NON_VALID;

@Service("executorExecutionSignalValidator")
public class ExecutorExecutionSignalValidator extends AbstractExecutionSignalValidator {

    private final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache;

    private final ExecutionTransitionContainer executionTransitionContainer;

    public ExecutorExecutionSignalValidator(final @NonNull Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache,
                                            final @NonNull DTOService<UserDTO, Long> userService,
                                            final @NonNull DTOService<RequestChainDTO, Long> requestChainService,
                                            final @NonNull ExecutionTransitionContainer executionTransitionContainer) {
        super(userService, requestChainService);

        this.executionStateCache = executionStateCache;
        this.executionTransitionContainer = executionTransitionContainer;
    }

    @Override
    protected void processNotNullSignalTypeValidation(final @NonNull ExecutionSignal signal,
                                                      final @NonNull ValidationResult validationResult) {
        final ExecutionStateCacheKey executionStateCacheKey = signal.getKey();
        final ExecutionState executionState = executionStateCache.get(executionStateCacheKey);

        if (executionState == null) {
            validationResult.setType(NON_VALID);
            validationResult.getDescriptions().add("State can't be null at execution stage");
        } else {
            final ExecutionStateType currentExecutionStateType = executionState.getType();

            if (!currentExecutionStateType.isPendingState()) {
                validationResult.setType(NON_VALID);
                validationResult.getDescriptions().add("Only pending states are valid at execution stage");
            } else {
                final ExecutionSignalType signalType = signal.getType();

                if (!executionTransitionContainer.getValidSignalTypesForState(currentExecutionStateType).contains(signalType)) {
                    validationResult.setType(NON_VALID);
                    validationResult.getDescriptions().
                            add("Signal '" + signalType + "' is not valid for state '" + currentExecutionStateType + "'");
                }
            }
        }
    }

}
