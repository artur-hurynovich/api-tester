package com.hurynovich.api_tester.state_transition.state.impl;

import com.hurynovich.api_tester.state_transition.exception.StateException;
import com.hurynovich.api_tester.state_transition.state.State;
import com.hurynovich.api_tester.state_transition.state_transition_descriptor.StateTransitionDescriptor;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StateImpl implements State {

    private final String name;

    private final Collection<StateTransitionDescriptor> stateTransitionDescriptors;

    public StateImpl(final @NonNull String name,
                     final @NonNull Collection<StateTransitionDescriptor> stateTransitionDescriptors) {
        this.name = name;
        this.stateTransitionDescriptors = stateTransitionDescriptors;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getValidSignalNames() {
        return stateTransitionDescriptors.stream().
                map(StateTransitionDescriptor::getSignalName).
                collect(Collectors.toList());
    }

    @Override
    public String processTransition(final @NonNull String signalName) {
        if (getValidSignalNames().contains(signalName)) {
            return fetchStateNameBySignalName(signalName);
        } else {
            throw new StateException("'" + signalName + "' - is not a valid signal for state '" + getName() + "'");
        }
    }

    private String fetchStateNameBySignalName(final String signalName) {
        final List<StateTransitionDescriptor> descriptorsBySignal =
                stateTransitionDescriptors.stream().
                        filter(stateTransitionDescriptor -> stateTransitionDescriptor.getSignalName().equals(signalName)).
                        collect(Collectors.toList());

        if (descriptorsBySignal.isEmpty()) {
            throw new StateException("No transition descriptors found for state '" + getName() +
                    "' and signal '" + signalName + "'");
        } else if (descriptorsBySignal.size() > 1) {
            throw new StateException("More than 1 transition descriptor found for state '" + getName() +
                    "' and signal '" + signalName + "'");
        } else {
            return descriptorsBySignal.iterator().next().getStateName();
        }
    }

}
