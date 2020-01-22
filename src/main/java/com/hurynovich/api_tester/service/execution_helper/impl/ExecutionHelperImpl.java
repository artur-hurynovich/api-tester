package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
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
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.hurynovich.api_tester.model.enumeration.ExecutionSignalType.RUN;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.PENDING_RUNNING;

@Service
public class ExecutionHelperImpl implements ExecutionHelper {

    private final ExecutionTransitionContainer executionTransitionContainer;

    private final Cache<GenericExecutionCacheKey, ExecutionState> executionStateCache;
    private final Cache<GenericExecutionCacheKey, ExecutionLogDTO> executionLogCache;

    private final DTOService<RequestChainDTO, Long> requestChainService;

    public ExecutionHelperImpl(final @NonNull ExecutionTransitionContainer executionTransitionContainer,
                               final @NonNull Cache<GenericExecutionCacheKey, ExecutionState> executionStateCache,
                               final @NonNull Cache<GenericExecutionCacheKey, ExecutionLogDTO> executionLogCache,
                               final @NonNull DTOService<RequestChainDTO, Long> requestChainService) {
        this.executionTransitionContainer = executionTransitionContainer;
        this.executionStateCache = executionStateCache;
        this.executionLogCache = executionLogCache;
        this.requestChainService = requestChainService;
    }

    @Override
    public ExecutionState getExecutionState(final @NonNull GenericExecutionCacheKey key) {
        return executionStateCache.get(key);
    }

    @Override
    public ExecutionState updateExecutionStateCache(final @NonNull ExecutionSignal executionSignal) {
        final GenericExecutionCacheKey genericExecutionCacheKey = executionSignal.getKey();

        ExecutionState executionState = executionStateCache.get(genericExecutionCacheKey);
        if (executionState == null) {
            executionState = new ExecutionState();

            final RequestChainDTO requestChain = requestChainService.getById(genericExecutionCacheKey.getRequestChainId());
            executionState.setRequests(requestChain.getRequests());
        }

        executionState.setType(resolveTransitionToExecutionStateType(executionSignal));

        return executionStateCache.put(genericExecutionCacheKey, executionState);
    }

    @Override
    public ExecutionLogDTO getExecutionLog(final GenericExecutionCacheKey key) {
        return executionLogCache.get(key);
    }

    @Override
    public ExecutionStateType resolveTransitionToExecutionStateType(final @NonNull ExecutionSignal executionSignal) {
        final ExecutionState currentExecutionState = executionStateCache.get(executionSignal.getKey());

        final ExecutionStateType executionStateType;

        if (currentExecutionState != null) {
            final ExecutionStateType currentExecutionStateType = currentExecutionState.getType();
            final ExecutionSignalType executionSignalType = executionSignal.getType();
            final List<ExecutionStateType> transitionsToState =
                    executionTransitionContainer.getTransitionsToState(currentExecutionStateType, executionSignalType);

            if (transitionsToState.size() != 1) {
                executionStateType = ExecutionStateType.errorStateType();
            } else {
                executionStateType = transitionsToState.get(0);
            }
        } else {
            if (ExecutionSignalType.initialSignalType() == executionSignal.getType()) {
                executionStateType = PENDING_RUNNING;
            } else {
                executionStateType = ExecutionStateType.errorStateType();
            }
        }

        return executionStateType;
    }

    @Override
    public List<ExecutionSignalType> resolveValidSignalTypesOnInit(final @Nullable ExecutionState executionState) {
        if (executionState == null) {
            return Collections.singletonList(RUN);
        } else {
            final ExecutionStateType currentExecutionStateType = executionState.getType();

            if (currentExecutionStateType.isPendingState()) {
                return Collections.emptyList();
            } else {
                return executionTransitionContainer.getValidSignalTypesForState(currentExecutionStateType);
            }
        }
    }

    @Override
    public List<ExecutionSignalType> resolveValidSignalTypesOnExecution(final @Nullable ExecutionState executionState) {
        if (executionState == null) {
            return Collections.emptyList();
        } else {
            final ExecutionStateType currentExecutionStateType = executionState.getType();

            if (currentExecutionStateType.isPendingState()) {
                return executionTransitionContainer.getValidSignalTypesForState(currentExecutionStateType);
            } else {
                return Collections.emptyList();
            }
        }
    }

}
