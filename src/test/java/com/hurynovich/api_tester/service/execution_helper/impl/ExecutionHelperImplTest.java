package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.cache.impl.ExecutionLogCache;
import com.hurynovich.api_tester.cache.impl.ExecutionStateCache;
import com.hurynovich.api_tester.configuration.APITesterConfiguration;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.state_transition.exception.StateException;
import com.hurynovich.api_tester.state_transition.signal.SignalName;
import com.hurynovich.api_tester.state_transition.state.State;
import com.hurynovich.api_tester.state_transition.state.StateName;
import com.hurynovich.api_tester.state_transition.state_manager.StateManager;
import com.hurynovich.api_tester.state_transition.state_manager.impl.StateManagerImpl;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class ExecutionHelperImplTest {

    private final Set<State> availableStates = new APITesterConfiguration().availableStates();

    private final StateManager stateManager = new StateManagerImpl(availableStates);

    private final Cache<ExecutionCacheKey, ExecutionState> executionStateCache = new ExecutionStateCache();

    private final Cache<ExecutionCacheKey, ExecutionLogDTO> executionLogCache = new ExecutionLogCache();

    private final ExecutionHelper executionHelper = new ExecutionHelperImpl(stateManager, executionStateCache,
            executionLogCache);

    @Test
    public void initExecutionStateCacheTest() {
        final RequestContainerDTO requestContainer =
                RequestTestHelper.generateRandomRequestContainerDTOs(1).iterator().next();

        final ExecutionCacheKey executionCacheKey = executionHelper.initExecutionStateCache(requestContainer);

        Assertions.assertNotNull(executionCacheKey);
        Assertions.assertNotNull(executionCacheKey.getExecutionKey());

        final ExecutionState executionState = executionHelper.getExecutionState(executionCacheKey);

        Assertions.assertNotNull(executionState);
        Assertions.assertEquals(StateName.INIT, executionState.getState().getName());
    }

    @Test
    public void updateExecutionStateCacheSuccessTest() {
        checkUpdateExecutionStateCache(StateName.INIT, SignalName.RUN, StateName.PENDING_RUNNING);
        checkUpdateExecutionStateCache(StateName.INIT, SignalName.PAUSE);
        checkUpdateExecutionStateCache(StateName.INIT, SignalName.RESUME);
        checkUpdateExecutionStateCache(StateName.INIT, SignalName.STOP);

        checkUpdateExecutionStateCache(StateName.PENDING_RUNNING, SignalName.RUN, StateName.RUNNING);
        checkUpdateExecutionStateCache(StateName.PENDING_RUNNING, SignalName.PAUSE);
        checkUpdateExecutionStateCache(StateName.PENDING_RUNNING, SignalName.RESUME, StateName.RUNNING);
        checkUpdateExecutionStateCache(StateName.PENDING_RUNNING, SignalName.STOP);

        checkUpdateExecutionStateCache(StateName.RUNNING, SignalName.RUN);
        checkUpdateExecutionStateCache(StateName.RUNNING, SignalName.PAUSE, StateName.PENDING_PAUSED);
        checkUpdateExecutionStateCache(StateName.RUNNING, SignalName.RESUME);
        checkUpdateExecutionStateCache(StateName.RUNNING, SignalName.STOP, StateName.PENDING_STOPPED);

        checkUpdateExecutionStateCache(StateName.PENDING_PAUSED, SignalName.RUN);
        checkUpdateExecutionStateCache(StateName.PENDING_PAUSED, SignalName.PAUSE, StateName.PAUSED);
        checkUpdateExecutionStateCache(StateName.PENDING_PAUSED, SignalName.RESUME);
        checkUpdateExecutionStateCache(StateName.PENDING_PAUSED, SignalName.STOP);

        checkUpdateExecutionStateCache(StateName.PAUSED, SignalName.RUN);
        checkUpdateExecutionStateCache(StateName.PAUSED, SignalName.PAUSE);
        checkUpdateExecutionStateCache(StateName.PAUSED, SignalName.RESUME, StateName.PENDING_RUNNING);
        checkUpdateExecutionStateCache(StateName.PAUSED, SignalName.STOP, StateName.PENDING_STOPPED);

        checkUpdateExecutionStateCache(StateName.PENDING_STOPPED, SignalName.RUN);
        checkUpdateExecutionStateCache(StateName.PENDING_STOPPED, SignalName.PAUSE);
        checkUpdateExecutionStateCache(StateName.PENDING_STOPPED, SignalName.RESUME);
        checkUpdateExecutionStateCache(StateName.PENDING_STOPPED, SignalName.STOP, StateName.STOPPED);

        checkUpdateExecutionStateCache(StateName.STOPPED, SignalName.RUN);
        checkUpdateExecutionStateCache(StateName.STOPPED, SignalName.PAUSE);
        checkUpdateExecutionStateCache(StateName.STOPPED, SignalName.RESUME);
        checkUpdateExecutionStateCache(StateName.STOPPED, SignalName.STOP);

        checkUpdateExecutionStateCache(StateName.FINISHED, SignalName.RUN);
        checkUpdateExecutionStateCache(StateName.FINISHED, SignalName.PAUSE);
        checkUpdateExecutionStateCache(StateName.FINISHED, SignalName.RESUME);
        checkUpdateExecutionStateCache(StateName.FINISHED, SignalName.STOP);

        checkUpdateExecutionStateCache(StateName.ERROR, SignalName.RUN);
        checkUpdateExecutionStateCache(StateName.ERROR, SignalName.PAUSE);
        checkUpdateExecutionStateCache(StateName.ERROR, SignalName.RESUME);
        checkUpdateExecutionStateCache(StateName.ERROR, SignalName.STOP);
    }

    private void checkUpdateExecutionStateCache(final String currentStateName, final String signalName,
                                                final String updatedStateName) {
        final ExecutionCacheKey executionCacheKey = ExecutionTestHelper.buildExecutionCacheKey();

        final ExecutionState executionState = ExecutionTestHelper.buildExecutionState(currentStateName);

        executionStateCache.put(executionCacheKey, executionState);

        final ExecutionSignal executionSignal = ExecutionTestHelper.buildExecutionSignal(signalName);
        executionSignal.setExecutionCacheKey(executionCacheKey);

        final ExecutionState updatedExecutionState = executionHelper.updateExecutionStateCache(executionSignal);

        Assertions.assertEquals(updatedStateName, updatedExecutionState.getState().getName());
    }

    private void checkUpdateExecutionStateCache(final String currentStateName, final String signalName) {
        final ExecutionCacheKey executionCacheKey = ExecutionTestHelper.buildExecutionCacheKey();

        final ExecutionState executionState = ExecutionTestHelper.buildExecutionState(currentStateName);

        executionStateCache.put(executionCacheKey, executionState);

        final ExecutionSignal executionSignal = ExecutionTestHelper.buildExecutionSignal(signalName);
        executionSignal.setExecutionCacheKey(executionCacheKey);

        Assertions.assertThrows(StateException.class,
                () -> executionHelper.updateExecutionStateCache(executionSignal),
                "'" + signalName + "' - is not a valid signal for state '" + currentStateName + "'");
    }

    @Test
    public void resolveValidSignalNamesOnInitTest() {
        List<String> signalNames = executionHelper.resolveValidSignalNamesOnInit(null);

        Assertions.assertEquals(1, signalNames.size());
        Assertions.assertTrue(signalNames.contains(SignalName.RUN));

        final State randomPendingState = ExecutionTestHelper.getRandomPendingState();
        signalNames = executionHelper.
                resolveValidSignalNamesOnInit(ExecutionTestHelper.buildExecutionState(randomPendingState.getName()));

        Assertions.assertTrue(signalNames.isEmpty());

        final State randomNotPendingState = ExecutionTestHelper.getRandomNotPendingState();
        signalNames = executionHelper.
                resolveValidSignalNamesOnInit(ExecutionTestHelper.buildExecutionState(randomNotPendingState.getName()));

        Assertions.assertEquals(randomNotPendingState.getValidSignalNames(), signalNames);
    }

    @Test
    public void resolveValidSignalNamesOnExecutionTest() {
        List<String> signalNames = executionHelper.resolveValidSignalNamesOnExecution(null);

        Assertions.assertTrue(signalNames.isEmpty());

        final State randomPendingState = ExecutionTestHelper.getRandomPendingState();
        signalNames = executionHelper.
                resolveValidSignalNamesOnExecution(ExecutionTestHelper.buildExecutionState(randomPendingState.getName()));

        Assertions.assertEquals(randomPendingState.getValidSignalNames(), signalNames);

        final State randomNotPendingState = ExecutionTestHelper.getRandomNotPendingState();
        signalNames = executionHelper.
                resolveValidSignalNamesOnExecution(ExecutionTestHelper.buildExecutionState(randomNotPendingState.getName()));

        Assertions.assertTrue(signalNames.isEmpty());
    }


}
