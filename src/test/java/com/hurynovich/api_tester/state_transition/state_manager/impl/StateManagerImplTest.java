package com.hurynovich.api_tester.state_transition.state_manager.impl;

import com.hurynovich.api_tester.configuration.APITesterConfiguration;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.state_transition.exception.StateException;
import com.hurynovich.api_tester.state_transition.signal.SignalName;
import com.hurynovich.api_tester.state_transition.state.State;
import com.hurynovich.api_tester.state_transition.state.StateName;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class StateManagerImplTest {

    private final Set<State> availableStates = new APITesterConfiguration().availableStates();

    private final StateManagerImpl stateManager = new StateManagerImpl(availableStates);

    @Test
    public void processTransitionTest() {
        executeTest(StateName.INIT, SignalName.RUN, StateName.PENDING_RUNNING);
        executeTest(StateName.INIT, SignalName.PAUSE);
        executeTest(StateName.INIT, SignalName.RESUME);
        executeTest(StateName.INIT, SignalName.STOP);

        executeTest(StateName.PENDING_RUNNING, SignalName.RUN, StateName.RUNNING);
        executeTest(StateName.PENDING_RUNNING, SignalName.PAUSE);
        executeTest(StateName.PENDING_RUNNING, SignalName.RESUME, StateName.RUNNING);
        executeTest(StateName.PENDING_RUNNING, SignalName.STOP);

        executeTest(StateName.RUNNING, SignalName.RUN);
        executeTest(StateName.RUNNING, SignalName.PAUSE, StateName.PENDING_PAUSED);
        executeTest(StateName.RUNNING, SignalName.RESUME);
        executeTest(StateName.RUNNING, SignalName.STOP, StateName.PENDING_STOPPED);

        executeTest(StateName.PENDING_PAUSED, SignalName.RUN);
        executeTest(StateName.PENDING_PAUSED, SignalName.PAUSE, StateName.PAUSED);
        executeTest(StateName.PENDING_PAUSED, SignalName.RESUME);
        executeTest(StateName.PENDING_PAUSED, SignalName.STOP);

        executeTest(StateName.PAUSED, SignalName.RUN);
        executeTest(StateName.PAUSED, SignalName.PAUSE);
        executeTest(StateName.PAUSED, SignalName.RESUME, StateName.PENDING_RUNNING);
        executeTest(StateName.PAUSED, SignalName.STOP, StateName.PENDING_STOPPED);

        executeTest(StateName.PENDING_STOPPED, SignalName.RUN);
        executeTest(StateName.PENDING_STOPPED, SignalName.PAUSE);
        executeTest(StateName.PENDING_STOPPED, SignalName.RESUME);
        executeTest(StateName.PENDING_STOPPED, SignalName.STOP, StateName.STOPPED);

        executeTest(StateName.STOPPED, SignalName.RUN);
        executeTest(StateName.STOPPED, SignalName.PAUSE);
        executeTest(StateName.STOPPED, SignalName.RESUME);
        executeTest(StateName.STOPPED, SignalName.STOP);

        executeTest(StateName.FINISHED, SignalName.RUN);
        executeTest(StateName.FINISHED, SignalName.PAUSE);
        executeTest(StateName.FINISHED, SignalName.RESUME);
        executeTest(StateName.FINISHED, SignalName.STOP);

        executeTest(StateName.ERROR, SignalName.RUN);
        executeTest(StateName.ERROR, SignalName.PAUSE);
        executeTest(StateName.ERROR, SignalName.RESUME);
        executeTest(StateName.ERROR, SignalName.STOP);
    }

    private void executeTest(final String fromStateName, final String signalName, final String toStateName) {
        final ExecutionState executionState = ExecutionTestHelper.buildExecutionState(fromStateName);
        final ExecutionSignal executionSignal = ExecutionTestHelper.buildExecutionSignal(signalName);
        stateManager.processTransition(executionState, executionSignal);

        Assertions.assertEquals(toStateName, executionState.getState().getName());
    }

    private void executeTest(final String fromStateName, final String signalName) {
        final ExecutionState executionState = ExecutionTestHelper.buildExecutionState(fromStateName);
        final ExecutionSignal executionSignal = ExecutionTestHelper.buildExecutionSignal(signalName);

        Assertions.assertThrows(StateException.class, () -> stateManager.
                processTransition(executionState, executionSignal));
    }

}
