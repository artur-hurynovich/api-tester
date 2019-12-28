package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.service.execution_transition_container.ExecutionTransitionContainer;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

import static com.hurynovich.api_tester.model.enumeration.ExecutionSignalType.RUN;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.ERROR;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.PENDING_RUNNING;

public class ExecutionHelperImpl implements ExecutionHelper {

    private final ExecutionTransitionContainer executionTransitionContainer;

    private final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache;

    private final DTOService<RequestChainDTO, Long> requestChainService;

    public ExecutionHelperImpl(final @NonNull ExecutionTransitionContainer executionTransitionContainer,
                               final @NonNull Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache,
                               final @NonNull DTOService<RequestChainDTO, Long> requestChainService) {
        this.executionTransitionContainer = executionTransitionContainer;
        this.executionStateCache = executionStateCache;
        this.requestChainService = requestChainService;
    }

    @Override
    public ExecutionState updateExecutionStateCache(final @NonNull ExecutionSignal executionSignal) {
        final Long userId = executionSignal.getUserId();
        final Long requestChainId = executionSignal.getRequestChainId();
        final ExecutionStateCacheKey executionStateCacheKey = new ExecutionStateCacheKey(userId, requestChainId);

        ExecutionState executionState = executionStateCache.get(executionStateCacheKey);
        if (executionState == null) {
            executionState = new ExecutionState();

            final RequestChainDTO requestChain = requestChainService.getById(executionSignal.getRequestChainId());
            executionState.setRequests(requestChain.getRequests());
        }

        executionState.setType(resolveTransitionToExecutionStateType(executionSignal));

        return executionStateCache.put(executionStateCacheKey, executionState);
    }

    @Override
    public List<ExecutionSignalType> resolveValidExecutionSignalTypes(final @Nullable ExecutionState executionState) {
        if (executionState == null) {
            return Collections.singletonList(RUN);
        } else {
            return executionTransitionContainer.getValidSignalTypesForState(executionState.getType());
        }
    }

    @Override
    public ExecutionStateType resolveTransitionToExecutionStateType(final @NonNull ExecutionSignal executionSignal) {
        final ExecutionState currentExecutionState = getCurrentExecutionState(executionSignal);

        final ExecutionStateType executionStateType;

        if (currentExecutionState != null) {
            final ExecutionStateType currentExecutionStateType = currentExecutionState.getType();
            final ExecutionSignalType executionSignalType = executionSignal.getType();
            final List<ExecutionStateType> transitionsToState =
                    executionTransitionContainer.getTransitionsToState(currentExecutionStateType, executionSignalType);

            if (transitionsToState.size() != 1) {
                executionStateType = ERROR;
            } else {
                executionStateType = transitionsToState.get(0);
            }
        } else {
            if (executionSignal.getType() == RUN) {
                executionStateType = PENDING_RUNNING;
            } else {
                executionStateType = ERROR;
            }
        }

        return executionStateType;
    }

    private ExecutionState getCurrentExecutionState(final @NonNull ExecutionSignal executionSignal) {
        final Long userId = executionSignal.getUserId();
        final Long requestChainId = executionSignal.getRequestChainId();
        final ExecutionStateCacheKey executionStateCacheKey = new ExecutionStateCacheKey(userId, requestChainId);

        return executionStateCache.get(executionStateCacheKey);
    }

}
