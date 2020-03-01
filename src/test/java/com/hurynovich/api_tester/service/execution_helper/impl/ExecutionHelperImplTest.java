package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.service.execution_transition_container.ExecutionTransitionContainer;
import com.hurynovich.api_tester.service.execution_transition_container.impl.ExecutionTransitionContainerImpl;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

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

@ExtendWith(MockitoExtension.class)
public class ExecutionHelperImplTest {

    private final ExecutionState PENDING_RUNNING_EXECUTION_STATE = buildExecutionState(PENDING_RUNNING);
    private final ExecutionState RUNNING_EXECUTION_STATE = buildExecutionState(RUNNING);
    private final ExecutionState PENDING_PAUSED_EXECUTION_STATE = buildExecutionState(PENDING_PAUSED);
    private final ExecutionState PAUSED_EXECUTION_STATE = buildExecutionState(PAUSED);
    private final ExecutionState PENDING_STOPPED_EXECUTION_STATE = buildExecutionState(PENDING_STOPPED);
    private final ExecutionState STOPPED_EXECUTION_STATE = buildExecutionState(STOPPED);
    private final ExecutionState FINISHED_EXECUTION_STATE = buildExecutionState(FINISHED);
    private final ExecutionState ERROR_EXECUTION_STATE = buildExecutionState(ERROR);

    private final ExecutionTransitionContainer EXECUTION_TRANSITION_CONTAINER =
            new ExecutionTransitionContainerImpl();

    @Mock
    private Cache<GenericExecutionCacheKey, ExecutionState> executionStateCache;

    @Mock
    private Cache<GenericExecutionCacheKey, ExecutionLogDocument> executionLogCache;

    @Mock
    private DTOService<RequestChainDTO, Long> requestChainService;

    private ExecutionHelper executionHelper;

    @BeforeEach
    public void init() {
        executionHelper = new ExecutionHelperImpl(EXECUTION_TRANSITION_CONTAINER,
                executionStateCache, executionLogCache, requestChainService);
    }

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

    @Test
    public void resolveValidSignalTypesOnInitTest() {
        List<ExecutionSignalType> executionSignalTypes =
                executionHelper.resolveValidSignalNamesOnInit(null);
        Assertions.assertEquals(1, executionSignalTypes.size());
        Assertions.assertTrue(executionSignalTypes.contains(RUN));

        final ExecutionStateType randomPendingStateType = getRandomPendingStateType();
        executionSignalTypes = executionHelper.
                resolveValidSignalNamesOnInit(ExecutionTestHelper.buildExecutionState(randomPendingStateType));
        Assertions.assertTrue(executionSignalTypes.isEmpty());

        final ExecutionStateType randomNotPendingStateType = getRandomNotPendingStateType();
        executionSignalTypes = executionHelper.
                resolveValidSignalNamesOnInit(ExecutionTestHelper.buildExecutionState(randomNotPendingStateType));
        Assertions.assertEquals(EXECUTION_TRANSITION_CONTAINER.getValidSignalTypesForState(randomNotPendingStateType),
                executionSignalTypes);
    }

    @Test
    public void resolveValidSignalTypesOnExecutionTest() {
        List<ExecutionSignalType> executionSignalTypes =
                executionHelper.resolveValidSignalNamesOnExecution(null);
        Assertions.assertTrue(executionSignalTypes.isEmpty());

        final ExecutionStateType randomPendingStateType = getRandomPendingStateType();
        executionSignalTypes = executionHelper.
                resolveValidSignalNamesOnExecution(ExecutionTestHelper.buildExecutionState(randomPendingStateType));
        Assertions.assertEquals(EXECUTION_TRANSITION_CONTAINER.getValidSignalTypesForState(randomPendingStateType),
                executionSignalTypes);

        final ExecutionStateType randomNotPendingStateType = getRandomNotPendingStateType();
        executionSignalTypes = executionHelper.
                resolveValidSignalNamesOnExecution(ExecutionTestHelper.buildExecutionState(randomNotPendingStateType));
        Assertions.assertTrue(executionSignalTypes.isEmpty());
    }

    private void checkTransitionToExecutionStateType(final ExecutionState currentState,
                                                     final ExecutionSignal signal,
                                                     final ExecutionStateType expectedExecutionStateType) {
        Mockito.when(executionStateCache.get(Mockito.any(GenericExecutionCacheKey.class))).thenReturn(currentState);

        final ExecutionStateType executionStateType = executionHelper.resolveTransitionToExecutionStateType(signal);
        Assertions.assertEquals(expectedExecutionStateType, executionStateType);
    }

    private ExecutionStateType getRandomPendingStateType() {
        return Stream.of(ExecutionStateType.values()).
                filter(ExecutionStateType::isPendingState).findAny().orElse(null);
    }

    private ExecutionStateType getRandomNotPendingStateType() {
        return Stream.of(ExecutionStateType.values()).
                filter(executionStateType -> !executionStateType.isPendingState()).findAny().orElse(null);
    }

}
