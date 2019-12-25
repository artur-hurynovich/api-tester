package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

public class ExecutionHelperImplTest {

    private static final Cache<ExecutionStateCacheKey, ExecutionState> EXECUTION_STATE_CACHE = Mockito.mock(Cache.class);

    private static final ExecutionHelper EXECUTION_HELPER = new ExecutionHelperImpl(EXECUTION_STATE_CACHE);

    final ExecutionState PENDING_RUNNING_EXECUTION_STATE = buildExecutionState(PENDING_RUNNING);
    final ExecutionState RUNNING_EXECUTION_STATE = buildExecutionState(RUNNING);
    final ExecutionState PENDING_PAUSED_EXECUTION_STATE = buildExecutionState(PENDING_PAUSED);
    final ExecutionState PAUSED_EXECUTION_STATE = buildExecutionState(PAUSED);
    final ExecutionState PENDING_STOPPED_EXECUTION_STATE = buildExecutionState(PENDING_STOPPED);
    final ExecutionState STOPPED_EXECUTION_STATE = buildExecutionState(STOPPED);
    final ExecutionState FINISHED_EXECUTION_STATE = buildExecutionState(FINISHED);
    final ExecutionState ERROR_EXECUTION_STATE = buildExecutionState(ERROR);

    @Test
    public void resolveValidExecutionSignalTypesTest() {
        checkValidExecutionSignalTypes(null, Collections.singletonList(RUN));
        checkValidExecutionSignalTypes(PENDING_RUNNING_EXECUTION_STATE, Collections.singletonList(RUN));
        checkValidExecutionSignalTypes(RUNNING_EXECUTION_STATE, Arrays.asList(PAUSE, STOP));
        checkValidExecutionSignalTypes(PENDING_PAUSED_EXECUTION_STATE, Collections.singletonList(PAUSE));
        checkValidExecutionSignalTypes(PAUSED_EXECUTION_STATE, Arrays.asList(RESUME, STOP));
        checkValidExecutionSignalTypes(PENDING_STOPPED_EXECUTION_STATE, Collections.singletonList(STOP));
        checkValidExecutionSignalTypes(STOPPED_EXECUTION_STATE, Collections.singletonList(RUN));
        checkValidExecutionSignalTypes(FINISHED_EXECUTION_STATE, Collections.singletonList(RUN));
        checkValidExecutionSignalTypes(ERROR_EXECUTION_STATE, Collections.singletonList(RUN));
    }

    @Test
    public void resolveTransitionToExecutionStateTypeTest() {
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), null, PENDING_RUNNING);
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), PENDING_RUNNING_EXECUTION_STATE, RUNNING);
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), RUNNING_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), PENDING_PAUSED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), PAUSED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), PENDING_STOPPED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), STOPPED_EXECUTION_STATE, PENDING_RUNNING);
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), FINISHED_EXECUTION_STATE, PENDING_RUNNING);
        checkTransitionToExecutionStateType(buildExecutionSignal(RUN), ERROR_EXECUTION_STATE, PENDING_RUNNING);

        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), null, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), PENDING_RUNNING_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), RUNNING_EXECUTION_STATE, PENDING_PAUSED);
        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), PENDING_PAUSED_EXECUTION_STATE, PAUSED);
        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), PAUSED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), PENDING_STOPPED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), STOPPED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), FINISHED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(PAUSE), ERROR_EXECUTION_STATE, ERROR);

        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), null, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), PENDING_RUNNING_EXECUTION_STATE, RUNNING);
        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), RUNNING_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), PENDING_PAUSED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), PAUSED_EXECUTION_STATE, PENDING_RUNNING);
        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), PENDING_STOPPED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), STOPPED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), FINISHED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(RESUME), ERROR_EXECUTION_STATE, ERROR);

        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), null, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), PENDING_RUNNING_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), RUNNING_EXECUTION_STATE, PENDING_STOPPED);
        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), PENDING_PAUSED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), PAUSED_EXECUTION_STATE, PENDING_STOPPED);
        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), PENDING_STOPPED_EXECUTION_STATE, STOPPED);
        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), STOPPED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), FINISHED_EXECUTION_STATE, ERROR);
        checkTransitionToExecutionStateType(buildExecutionSignal(STOP), ERROR_EXECUTION_STATE, ERROR);
    }

    private ExecutionState buildExecutionState(final ExecutionStateType type) {
        final ExecutionState executionState = new ExecutionState();

        executionState.setType(type);

        return executionState;
    }

    private ExecutionSignal buildExecutionSignal(final ExecutionSignalType type) {
        final ExecutionSignal executionSignal = new ExecutionSignal();

        executionSignal.setType(type);

        return executionSignal;
    }

    private void checkValidExecutionSignalTypes(final ExecutionState executionState,
                                                final List<ExecutionSignalType> expectedSignalTypes) {
        final List<ExecutionSignalType> executionSignalTypes = EXECUTION_HELPER.resolveValidExecutionSignalTypes(executionState);

        Assertions.assertEquals(expectedSignalTypes, executionSignalTypes);
    }

    private void checkTransitionToExecutionStateType(final ExecutionSignal signal,
                                                     final ExecutionState currentState,
                                                     final ExecutionStateType expectedExecutionStateType) {
        final Long userId = (long) RandomValueGenerator.generateRandomPositiveInt();
        final Long requestChainId = (long) RandomValueGenerator.generateRandomPositiveInt();
        signal.setUserId(userId);
        signal.setRequestChainId(requestChainId);

        final ExecutionStateCacheKey executionStateCacheKey = new ExecutionStateCacheKey(userId, requestChainId);
        Mockito.when(EXECUTION_STATE_CACHE.get(executionStateCacheKey)).thenReturn(currentState);

        final ExecutionStateType executionStateType = EXECUTION_HELPER.resolveTransitionToExecutionStateType(signal);
        Assertions.assertEquals(expectedExecutionStateType, executionStateType);
    }

}
