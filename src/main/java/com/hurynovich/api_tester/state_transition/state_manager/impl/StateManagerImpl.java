package com.hurynovich.api_tester.state_transition.state_manager.impl;

import com.hurynovich.api_tester.state_transition.exception.StateManagerException;
import com.hurynovich.api_tester.state_transition.has_signal.HasSignal;
import com.hurynovich.api_tester.state_transition.has_state.HasState;
import com.hurynovich.api_tester.state_transition.signal.Signal;
import com.hurynovich.api_tester.state_transition.state.State;
import com.hurynovich.api_tester.state_transition.state.StateName;
import com.hurynovich.api_tester.state_transition.state_manager.StateManager;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StateManagerImpl implements StateManager {

    private final Set<State> availableStates;

    private final State initState;

    public StateManagerImpl(final @NonNull Set<State> availableStates) {
        this.availableStates = availableStates;

        initState = availableStates.stream().
                filter(state -> state.getName().equals(StateName.INIT)).
                findFirst().
                orElseThrow(() -> new StateManagerException("No init state found"));
    }

    @Override
    public State getInitState() {
        return initState;
    }

    @Override
    public void processTransition(final @NonNull HasState hasState, final @NonNull HasSignal hasSignal) {
        final State fromState = hasState.getState();

        final Signal signal = hasSignal.getSignal();

        final String toStateName = fromState.processTransition(signal.getName());

        processTransition(hasState, toStateName);
    }

    @Override
    public void processTransition(final @NonNull HasState hasState, final @NonNull String toStateName) {
        final List<State> toStates = availableStates.stream().
                filter(availableState -> availableState.getName().equals(toStateName)).
                collect(Collectors.toList());

        if (toStates.isEmpty()) {
            throw new StateManagerException("Transition processing failed. No 'toState' with name '" +
                    toStateName + "' found");
        } else if (toStates.size() > 1) {
            throw new StateManagerException("Transition processing failed. More than one 'toState' with name '" +
                    toStateName + "' found");
        } else {
            final State toState = toStates.iterator().next();

            hasState.setState(toState);
        }
    }
}
