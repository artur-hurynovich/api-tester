package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
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
import java.util.UUID;

@Service
public class ExecutionHelperImpl implements ExecutionHelper {

    private final StateManager stateManager;

    private final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache;
    private final Cache<ExecutionStateCacheKey, ExecutionLogDTO> executionLogCache;

    public ExecutionHelperImpl(final @NonNull StateManager stateManager,
                               final @NonNull Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache,
                               final @NonNull Cache<ExecutionStateCacheKey, ExecutionLogDTO> executionLogCache) {
        this.stateManager = stateManager;
        this.executionStateCache = executionStateCache;
        this.executionLogCache = executionLogCache;
    }

    @Override
    public ExecutionState getExecutionState(final @NonNull ExecutionStateCacheKey key) {
        return executionStateCache.get(key);
    }

    @Override
    public ExecutionStateCacheKey initExecutionStateCache(final @NonNull RequestContainerDTO requestContainerDTO) {
        final ExecutionStateCacheKey executionStateCacheKey = new ExecutionStateCacheKey(UUID.randomUUID().toString());

        final ExecutionState executionState = new ExecutionState();

        executionState.setState(stateManager.getInitState());
        executionState.setRequests(requestContainerDTO.getRequests());

        executionStateCache.put(executionStateCacheKey, executionState);

        return executionStateCacheKey;
    }

    @Override
    public ExecutionState updateExecutionStateCache(final @NonNull ExecutionSignal executionSignal) {
        final ExecutionStateCacheKey executionStateCacheKey = executionSignal.getExecutionStateCacheKey();

        final ExecutionState executionState = executionStateCache.get(executionStateCacheKey);

        stateManager.processTransition(executionState, executionSignal);

        return executionStateCache.put(executionStateCacheKey, executionState);
    }

    @Override
    public ExecutionLogDTO getExecutionLog(final ExecutionStateCacheKey key) {
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
