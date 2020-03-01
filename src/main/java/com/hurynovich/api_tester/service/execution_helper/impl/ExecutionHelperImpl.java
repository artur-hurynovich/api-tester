package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.state_transition.signal.SignalName;
import com.hurynovich.api_tester.state_transition.state.State;
import com.hurynovich.api_tester.state_transition.state.StateName;
import com.hurynovich.api_tester.state_transition.state_manager.StateManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ExecutionHelperImpl implements ExecutionHelper {

    private final StateManager stateManager;

    private final Cache<GenericExecutionCacheKey, ExecutionState> executionStateCache;
    private final Cache<GenericExecutionCacheKey, ExecutionLogDocument> executionLogCache;

    private final DTOService<RequestChainDTO, Long> requestChainService;

    public ExecutionHelperImpl(final @NonNull StateManager stateManager,
                               final @NonNull Cache<GenericExecutionCacheKey, ExecutionState> executionStateCache,
                               final @NonNull Cache<GenericExecutionCacheKey, ExecutionLogDocument> executionLogCache,
                               final @NonNull DTOService<RequestChainDTO, Long> requestChainService) {
        this.stateManager = stateManager;
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

            final RequestChainDTO requestChain =
                    requestChainService.readById(genericExecutionCacheKey.getRequestChainId());
            executionState.setRequests(requestChain.getRequests());
        }

        stateManager.processTransition(executionState, executionSignal);

        return executionStateCache.put(genericExecutionCacheKey, executionState);
    }

    @Override
    public ExecutionLogDocument getExecutionLog(final GenericExecutionCacheKey key) {
        return executionLogCache.get(key);
    }

    @Override
    public List<String> resolveValidSignalNamesOnInit(final @Nullable ExecutionState executionState) {
        if (executionState == null) {
            return Collections.singletonList(SignalName.RUN);
        } else {
            final State state = executionState.getState();

            if (StateName.isPendingStateName(state.getName())) {
                return Collections.emptyList();
            } else {
                return state.getValidSignalNames();
            }
        }
    }

    @Override
    public List<String> resolveValidSignalNamesOnExecution(final @Nullable ExecutionState executionState) {
        if (executionState == null) {
            return Collections.emptyList();
        } else {
            final State state = executionState.getState();

            if (StateName.isPendingStateName(state.getName())) {
                return state.getValidSignalNames();
            } else {
                return Collections.emptyList();
            }
        }
    }

}
