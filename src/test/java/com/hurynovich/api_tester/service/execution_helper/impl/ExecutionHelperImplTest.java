package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.configuration.APITesterConfiguration;
import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.state_transition.signal.SignalName;
import com.hurynovich.api_tester.state_transition.state.State;
import com.hurynovich.api_tester.state_transition.state_manager.StateManager;
import com.hurynovich.api_tester.state_transition.state_manager.impl.StateManagerImpl;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ExecutionHelperImplTest {

    private final Set<State> availableStates = new APITesterConfiguration().availableStates();

    private final StateManager stateManager = new StateManagerImpl(availableStates);

    @Mock
    private Cache<GenericExecutionCacheKey, ExecutionState> executionStateCache;

    @Mock
    private Cache<GenericExecutionCacheKey, ExecutionLogDocument> executionLogCache;

    @Mock
    private DTOService<RequestChainDTO, Long> requestChainService;

    private ExecutionHelper executionHelper;

    @BeforeEach
    public void init() {
        executionHelper = new ExecutionHelperImpl(stateManager, executionStateCache,
                executionLogCache, requestChainService);
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
