package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.configuration.APITesterConfiguration;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.state_transition.signal.Signal;
import com.hurynovich.api_tester.state_transition.signal.impl.SignalImpl;
import com.hurynovich.api_tester.state_transition.state.State;
import com.hurynovich.api_tester.state_transition.state.StateName;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExecutionTestHelper {

    private static final Set<State> AVAILABLE_STATES =
            new APITesterConfiguration().availableStates();

    private ExecutionTestHelper() {

    }

    public static ExecutionState buildExecutionState(final String stateName) {
        final ExecutionState executionState = new ExecutionState();

        final State stateByName = AVAILABLE_STATES.stream().
                filter(state -> state.getName().equals(stateName)).
                collect(Collectors.toList()).
                iterator().next();

        executionState.setState(stateByName);

        return executionState;
    }

    public static ExecutionSignal buildExecutionSignal(final String signalName) {
        final ExecutionSignal executionSignal = new ExecutionSignal();

        final Signal signal = new SignalImpl(signalName);
        executionSignal.setSignal(signal);

        final ExecutionStateCacheKey key = buildExecutionStateCacheKey();
        executionSignal.setExecutionStateCacheKey(key);

        return executionSignal;
    }

    public static State getRandomPendingState() {
        return AVAILABLE_STATES.stream().
                filter(state -> StateName.isPendingStateName(state.getName())).
                findAny().
                orElse(null);
    }

    public static State getRandomNotPendingState() {
        return AVAILABLE_STATES.stream().
                filter(state -> !StateName.isPendingStateName(state.getName())).
                findAny().
                orElse(null);
    }

    public static String getRandomSignalName() {
        return AVAILABLE_STATES.stream().
                flatMap(state -> state.getValidSignalNames().stream()).
                findAny().
                orElse(null);
    }

    public static List<String> getAllSignalNames() {
        return AVAILABLE_STATES.stream().
                flatMap(state -> state.getValidSignalNames().stream()).
                collect(Collectors.toList());
    }

    public static ExecutionStateCacheKey buildExecutionStateCacheKey() {
        return new ExecutionStateCacheKey(UUID.randomUUID().toString());
    }

}
