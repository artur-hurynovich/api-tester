package com.hurynovich.api_tester.service.execution_transition_container.impl;

import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.service.execution_transition_container.ExecutionTransitionContainer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

@Component
public class ExecutionTransitionContainerImpl implements ExecutionTransitionContainer {

    private static final String NOT_ALL_STATES_ASSERTION_MESSAGE = "Not all executionStateTypes listed in container";

    private final MultiValueMap<ExecutionStateType, Transition> transitions;

    public ExecutionTransitionContainerImpl() {
        transitions = new LinkedMultiValueMap<>();

        transitions.add(PENDING_RUNNING, new Transition(RUN, RUNNING));
        transitions.add(PENDING_RUNNING, new Transition(RESUME, RUNNING));
        transitions.add(RUNNING, new Transition(PAUSE, PENDING_PAUSED));
        transitions.add(RUNNING, new Transition(STOP, PENDING_STOPPED));
        transitions.add(PENDING_PAUSED, new Transition(PAUSE, PAUSED));
        transitions.add(PAUSED, new Transition(RESUME, PENDING_RUNNING));
        transitions.add(PAUSED, new Transition(STOP, PENDING_STOPPED));
        transitions.add(PENDING_STOPPED, new Transition(STOP, STOPPED));
        transitions.add(STOPPED, new Transition(RUN, PENDING_RUNNING));
        transitions.add(FINISHED, new Transition(RUN, PENDING_RUNNING));
        transitions.add(ERROR, new Transition(RUN, PENDING_RUNNING));

        assert transitions.keySet().containsAll(Arrays.asList(ExecutionStateType.values())) :
                NOT_ALL_STATES_ASSERTION_MESSAGE;
    }

    @Override
    public List<ExecutionSignalType> getValidSignalTypesForState(final @NonNull ExecutionStateType stateType) {
        return transitions.get(stateType).stream().map(Transition::getSignalType).collect(Collectors.toList());
    }

    @Override
    public List<ExecutionStateType> getTransitionsToState(final @NonNull ExecutionStateType currentStateType,
                                                          final @NonNull ExecutionSignalType signalType) {
        return transitions.get(currentStateType).stream().
                filter(transition -> transition.getSignalType() == signalType).
                map(Transition::getStateType).
                collect(Collectors.toList());
    }

    private static class Transition {

        private final ExecutionSignalType signalType;

        private final ExecutionStateType stateType;

        public Transition(final @NonNull ExecutionSignalType signalType, final @NonNull ExecutionStateType stateType) {
            this.signalType = signalType;
            this.stateType = stateType;
        }

        public ExecutionSignalType getSignalType() {
            return signalType;
        }

        public ExecutionStateType getStateType() {
            return stateType;
        }
    }

}
