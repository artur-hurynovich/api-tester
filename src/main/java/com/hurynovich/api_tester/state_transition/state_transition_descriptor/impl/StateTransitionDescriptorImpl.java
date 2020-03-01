package com.hurynovich.api_tester.state_transition.state_transition_descriptor.impl;

import com.hurynovich.api_tester.state_transition.state_transition_descriptor.StateTransitionDescriptor;
import org.springframework.lang.NonNull;

public class StateTransitionDescriptorImpl implements StateTransitionDescriptor {

    private final String signalName;

    private final String stateName;

    public StateTransitionDescriptorImpl(final @NonNull String signalName, final @NonNull String stateName) {
        this.signalName = signalName;
        this.stateName = stateName;
    }

    @Override
    public String getSignalName() {
        return signalName;
    }

    @Override
    public String getStateName() {
        return stateName;
    }

}
