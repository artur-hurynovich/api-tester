package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hurynovich.api_tester.model.enumeration.ExecutionSignalType.PAUSE;
import static com.hurynovich.api_tester.model.enumeration.ExecutionSignalType.RESUME;
import static com.hurynovich.api_tester.model.enumeration.ExecutionSignalType.RUN;
import static com.hurynovich.api_tester.model.enumeration.ExecutionSignalType.STOP;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.ERROR;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.FINISHED;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.PAUSED;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.PENDING_PAUSED;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.PENDING_RUNNING;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.PENDING_STOPPED;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.RUNNING;
import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.STOPPED;

public class ExecutionHelperImpl implements ExecutionHelper {

    private static final Map<ExecutionStateType, List<ExecutionSignalType>> VALID_EXECUTION_SIGNAL_TYPES =
            initValidExecutionSignalTypes();

    private static final Map<Pair<ExecutionSignalType, ExecutionStateType>, ExecutionStateType> TRANSITION_TO_EXECUTION_STATE_TYPES =
            initTransitionToExecutionStateTypes();

    private final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache;

    public ExecutionHelperImpl(final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache) {
        this.executionStateCache = executionStateCache;
    }

    private static Map<ExecutionStateType, List<ExecutionSignalType>> initValidExecutionSignalTypes() {
        final Map<ExecutionStateType, List<ExecutionSignalType>> validExecutionSignalTypes = new EnumMap<>(ExecutionStateType.class);

        validExecutionSignalTypes.put(PENDING_RUNNING, Collections.singletonList(RUN));
        validExecutionSignalTypes.put(RUNNING, Arrays.asList(PAUSE, STOP));
        validExecutionSignalTypes.put(PENDING_PAUSED, Collections.singletonList(PAUSE));
        validExecutionSignalTypes.put(PAUSED, Arrays.asList(RESUME, STOP));
        validExecutionSignalTypes.put(PENDING_STOPPED, Collections.singletonList(STOP));
        validExecutionSignalTypes.put(STOPPED, Collections.singletonList(RUN));
        validExecutionSignalTypes.put(FINISHED, Collections.singletonList(RUN));
        validExecutionSignalTypes.put(ERROR, Collections.singletonList(RUN));

        return Collections.unmodifiableMap(validExecutionSignalTypes);
    }

    private static Map<Pair<ExecutionSignalType, ExecutionStateType>, ExecutionStateType> initTransitionToExecutionStateTypes() {
        final Map<Pair<ExecutionSignalType, ExecutionStateType>, ExecutionStateType> transitionToStatusMap = new HashMap<>();

        transitionToStatusMap.put(new ImmutablePair<>(RUN, null), PENDING_RUNNING);
        transitionToStatusMap.put(new ImmutablePair<>(RUN, PENDING_RUNNING), RUNNING);
        transitionToStatusMap.put(new ImmutablePair<>(RUN, STOPPED), PENDING_RUNNING);
        transitionToStatusMap.put(new ImmutablePair<>(RUN, FINISHED), PENDING_RUNNING);
        transitionToStatusMap.put(new ImmutablePair<>(RUN, ERROR), PENDING_RUNNING);

		transitionToStatusMap.put(new ImmutablePair<>(PAUSE, RUNNING), PENDING_PAUSED);
		transitionToStatusMap.put(new ImmutablePair<>(PAUSE, PENDING_PAUSED), PAUSED);

		transitionToStatusMap.put(new ImmutablePair<>(RESUME, PAUSED), PENDING_RUNNING);
		transitionToStatusMap.put(new ImmutablePair<>(RESUME, PENDING_RUNNING), RUNNING);

		transitionToStatusMap.put(new ImmutablePair<>(STOP, RUNNING), PENDING_STOPPED);
		transitionToStatusMap.put(new ImmutablePair<>(STOP, PAUSED), PENDING_STOPPED);
		transitionToStatusMap.put(new ImmutablePair<>(STOP, PENDING_STOPPED), STOPPED);

        return Collections.unmodifiableMap(transitionToStatusMap);
    }

    @Override
    public List<ExecutionSignalType> resolveValidExecutionSignalTypes(final @Nullable ExecutionState executionState) {
        if (executionState == null) {
            return Collections.singletonList(RUN);
        } else {
            return VALID_EXECUTION_SIGNAL_TYPES.get(executionState.getType());
        }
    }

    @Override
    public ExecutionStateType resolveTransitionToExecutionStateType(final @NonNull ExecutionSignal executionSignal) {
		final Pair<ExecutionSignalType, ExecutionStateType> pairOfSignalAndCurrentState = buildPairOfSignalAndCurrentState(executionSignal);

		return TRANSITION_TO_EXECUTION_STATE_TYPES.get(pairOfSignalAndCurrentState);
    }

    private Pair<ExecutionSignalType, ExecutionStateType> buildPairOfSignalAndCurrentState(final @NonNull ExecutionSignal executionSignal) {
		final ExecutionSignalType signalType = executionSignal.getType();

		final ExecutionState currentExecutionState = getCurrentExecutionState(executionSignal);
		final ExecutionStateType currentExecutionStateType;
		if (currentExecutionState != null) {
			currentExecutionStateType = currentExecutionState.getType();
		} else {
			currentExecutionStateType = null;
		}

		return ImmutablePair.of(signalType, currentExecutionStateType);
	}

	private ExecutionState getCurrentExecutionState(final @NonNull ExecutionSignal executionSignal) {
		final Long userId = executionSignal.getUserId();
		final Long requestChainId = executionSignal.getRequestChainId();
		final ExecutionStateCacheKey executionStateCacheKey = new ExecutionStateCacheKey(userId, requestChainId);

		return executionStateCache.get(executionStateCacheKey);
	}

}
