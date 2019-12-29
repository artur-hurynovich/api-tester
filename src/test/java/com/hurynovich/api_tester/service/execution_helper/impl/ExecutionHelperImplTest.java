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
import com.hurynovich.api_tester.service.execution_transition_container.impl.ExecutionTransitionContainerImpl;

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
import static com.hurynovich.api_tester.test_helper.ExecutionTestHelper.buildExecutionSignal;
import static com.hurynovich.api_tester.test_helper.ExecutionTestHelper.buildExecutionState;

public class ExecutionHelperImplTest {

    private static final Cache<ExecutionStateCacheKey, ExecutionState> EXECUTION_STATE_CACHE = Mockito.mock(Cache.class);

    private static final DTOService<RequestChainDTO, Long> REQUEST_CHAIN_SERVICE = Mockito.mock(DTOService.class);

    private static final ExecutionTransitionContainer EXECUTION_TRANSITION_CONTAINER =
            new ExecutionTransitionContainerImpl();

    private static final ExecutionHelper EXECUTION_HELPER = new ExecutionHelperImpl(EXECUTION_TRANSITION_CONTAINER,
            EXECUTION_STATE_CACHE, REQUEST_CHAIN_SERVICE);

    final ExecutionState PENDING_RUNNING_EXECUTION_STATE = buildExecutionState(PENDING_RUNNING);
    final ExecutionState RUNNING_EXECUTION_STATE = buildExecutionState(RUNNING);
    final ExecutionState PENDING_PAUSED_EXECUTION_STATE = buildExecutionState(PENDING_PAUSED);
    final ExecutionState PAUSED_EXECUTION_STATE = buildExecutionState(PAUSED);
    final ExecutionState PENDING_STOPPED_EXECUTION_STATE = buildExecutionState(PENDING_STOPPED);
    final ExecutionState STOPPED_EXECUTION_STATE = buildExecutionState(STOPPED);
    final ExecutionState FINISHED_EXECUTION_STATE = buildExecutionState(FINISHED);
    final ExecutionState ERROR_EXECUTION_STATE = buildExecutionState(ERROR);

    @Test
    public void resolveTransitionToExecutionStateTypeTest() {
        checkTransitionToExecutionStateType(null, buildExecutionSignal(RUN), PENDING_RUNNING);
        checkTransitionToExecutionStateType(PENDING_RUNNING_EXECUTION_STATE, buildExecutionSignal(RUN), RUNNING);
        checkTransitionToExecutionStateType(RUNNING_EXECUTION_STATE, buildExecutionSignal(RUN), ERROR);
        checkTransitionToExecutionStateType(PENDING_PAUSED_EXECUTION_STATE, buildExecutionSignal(RUN), ERROR);
        checkTransitionToExecutionStateType(PAUSED_EXECUTION_STATE, buildExecutionSignal(RUN), ERROR);
        checkTransitionToExecutionStateType(PENDING_STOPPED_EXECUTION_STATE, buildExecutionSignal(RUN), ERROR);
        checkTransitionToExecutionStateType(STOPPED_EXECUTION_STATE, buildExecutionSignal(RUN), PENDING_RUNNING);
        checkTransitionToExecutionStateType(FINISHED_EXECUTION_STATE, buildExecutionSignal(RUN), PENDING_RUNNING);
        checkTransitionToExecutionStateType(ERROR_EXECUTION_STATE, buildExecutionSignal(RUN), PENDING_RUNNING);

        checkTransitionToExecutionStateType(null, buildExecutionSignal(PAUSE), ERROR);
        checkTransitionToExecutionStateType(PENDING_RUNNING_EXECUTION_STATE, buildExecutionSignal(PAUSE), ERROR);
        checkTransitionToExecutionStateType(RUNNING_EXECUTION_STATE, buildExecutionSignal(PAUSE), PENDING_PAUSED);
        checkTransitionToExecutionStateType(PENDING_PAUSED_EXECUTION_STATE, buildExecutionSignal(PAUSE), PAUSED);
        checkTransitionToExecutionStateType(PAUSED_EXECUTION_STATE, buildExecutionSignal(PAUSE), ERROR);
        checkTransitionToExecutionStateType(PENDING_STOPPED_EXECUTION_STATE, buildExecutionSignal(PAUSE), ERROR);
        checkTransitionToExecutionStateType(STOPPED_EXECUTION_STATE, buildExecutionSignal(PAUSE), ERROR);
        checkTransitionToExecutionStateType(FINISHED_EXECUTION_STATE, buildExecutionSignal(PAUSE), ERROR);
        checkTransitionToExecutionStateType(ERROR_EXECUTION_STATE, buildExecutionSignal(PAUSE), ERROR);

        checkTransitionToExecutionStateType(null, buildExecutionSignal(RESUME), ERROR);
        checkTransitionToExecutionStateType(PENDING_RUNNING_EXECUTION_STATE, buildExecutionSignal(RESUME), RUNNING);
        checkTransitionToExecutionStateType(RUNNING_EXECUTION_STATE, buildExecutionSignal(RESUME), ERROR);
        checkTransitionToExecutionStateType(PENDING_PAUSED_EXECUTION_STATE, buildExecutionSignal(RESUME), ERROR);
        checkTransitionToExecutionStateType(PAUSED_EXECUTION_STATE, buildExecutionSignal(RESUME), PENDING_RUNNING);
        checkTransitionToExecutionStateType(PENDING_STOPPED_EXECUTION_STATE, buildExecutionSignal(RESUME), ERROR);
        checkTransitionToExecutionStateType(STOPPED_EXECUTION_STATE, buildExecutionSignal(RESUME), ERROR);
        checkTransitionToExecutionStateType(FINISHED_EXECUTION_STATE, buildExecutionSignal(RESUME), ERROR);
        checkTransitionToExecutionStateType(ERROR_EXECUTION_STATE, buildExecutionSignal(RESUME), ERROR);

        checkTransitionToExecutionStateType(null, buildExecutionSignal(STOP), ERROR);
        checkTransitionToExecutionStateType(PENDING_RUNNING_EXECUTION_STATE, buildExecutionSignal(STOP), ERROR);
        checkTransitionToExecutionStateType(RUNNING_EXECUTION_STATE, buildExecutionSignal(STOP), PENDING_STOPPED);
        checkTransitionToExecutionStateType(PENDING_PAUSED_EXECUTION_STATE, buildExecutionSignal(STOP), ERROR);
        checkTransitionToExecutionStateType(PAUSED_EXECUTION_STATE, buildExecutionSignal(STOP), PENDING_STOPPED);
        checkTransitionToExecutionStateType(PENDING_STOPPED_EXECUTION_STATE, buildExecutionSignal(STOP), STOPPED);
        checkTransitionToExecutionStateType(STOPPED_EXECUTION_STATE, buildExecutionSignal(STOP), ERROR);
        checkTransitionToExecutionStateType(FINISHED_EXECUTION_STATE, buildExecutionSignal(STOP), ERROR);
        checkTransitionToExecutionStateType(ERROR_EXECUTION_STATE, buildExecutionSignal(STOP), ERROR);
    }

    private void checkTransitionToExecutionStateType(final ExecutionState currentState,
                                                     final ExecutionSignal signal,
                                                     final ExecutionStateType expectedExecutionStateType) {
        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(currentState);

        final ExecutionStateType executionStateType = EXECUTION_HELPER.resolveTransitionToExecutionStateType(signal);
        Assertions.assertEquals(expectedExecutionStateType, executionStateType);
    }

}
