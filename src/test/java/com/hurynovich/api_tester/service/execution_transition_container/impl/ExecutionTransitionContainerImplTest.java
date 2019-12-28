package com.hurynovich.api_tester.service.execution_transition_container.impl;

import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.service.execution_transition_container.ExecutionTransitionContainer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

public class ExecutionTransitionContainerImplTest {

    private static final ExecutionTransitionContainer EXECUTION_TRANSITION_CONTAINER =
            new ExecutionTransitionContainerImpl();

    @Test
    public void getValidSignalTypesForStateTest() {
        checkValidSignalTypesForState(PENDING_RUNNING, Arrays.asList(RUN, RESUME));
        checkValidSignalTypesForState(RUNNING, Arrays.asList(PAUSE, STOP));
        checkValidSignalTypesForState(PENDING_PAUSED, Collections.singletonList(PAUSE));
        checkValidSignalTypesForState(PAUSED, Arrays.asList(RESUME, STOP));
        checkValidSignalTypesForState(PENDING_STOPPED, Collections.singletonList(STOP));
        checkValidSignalTypesForState(STOPPED, Collections.singletonList(RUN));
        checkValidSignalTypesForState(FINISHED, Collections.singletonList(RUN));
        checkValidSignalTypesForState(ERROR, Collections.singletonList(RUN));
    }

    @Test
    public void getTransitionsToStateTest() {
        checkTransitionsToState(PENDING_RUNNING, RUN, RUNNING);
        checkTransitionsToStateNotFound(RUNNING, RUN);
        checkTransitionsToStateNotFound(PENDING_PAUSED, RUN);
        checkTransitionsToStateNotFound(PAUSED, RUN);
        checkTransitionsToStateNotFound(PENDING_STOPPED, RUN);
        checkTransitionsToState(STOPPED, RUN, PENDING_RUNNING);
        checkTransitionsToState(FINISHED, RUN, PENDING_RUNNING);
        checkTransitionsToState(ERROR, RUN, PENDING_RUNNING);

        checkTransitionsToStateNotFound(PENDING_RUNNING, PAUSE);
        checkTransitionsToState(RUNNING, PAUSE, PENDING_PAUSED);
        checkTransitionsToState(PENDING_PAUSED, PAUSE, PAUSED);
        checkTransitionsToStateNotFound(PAUSED, PAUSE);
        checkTransitionsToStateNotFound(PENDING_STOPPED, PAUSE);
        checkTransitionsToStateNotFound(STOPPED, PAUSE);
        checkTransitionsToStateNotFound(FINISHED, PAUSE);
        checkTransitionsToStateNotFound(ERROR, PAUSE);

        checkTransitionsToState(PENDING_RUNNING, RESUME, RUNNING);
        checkTransitionsToStateNotFound(RUNNING, RESUME);
        checkTransitionsToStateNotFound(PENDING_PAUSED, RESUME);
        checkTransitionsToState(PAUSED, RESUME, PENDING_RUNNING);
        checkTransitionsToStateNotFound(PENDING_STOPPED, RESUME);
        checkTransitionsToStateNotFound(STOPPED, RESUME);
        checkTransitionsToStateNotFound(FINISHED, RESUME);
        checkTransitionsToStateNotFound(ERROR, RESUME);

        checkTransitionsToStateNotFound(PENDING_RUNNING, STOP);
        checkTransitionsToState(RUNNING, STOP, PENDING_STOPPED);
        checkTransitionsToStateNotFound(PENDING_PAUSED, STOP);
        checkTransitionsToState(PAUSED, STOP, PENDING_STOPPED);
        checkTransitionsToState(PENDING_STOPPED, STOP, STOPPED);
        checkTransitionsToStateNotFound(STOPPED, STOP);
        checkTransitionsToStateNotFound(FINISHED, STOP);
        checkTransitionsToStateNotFound(ERROR, STOP);
    }

    private void checkValidSignalTypesForState(final ExecutionStateType executionStateType,
                                               final List<ExecutionSignalType> expectedSignalTypes) {
        final List<ExecutionSignalType> executionSignalTypes =
                EXECUTION_TRANSITION_CONTAINER.getValidSignalTypesForState(executionStateType);

        Assertions.assertEquals(expectedSignalTypes, executionSignalTypes);
    }

    private void checkTransitionsToState(final ExecutionStateType currentStateType,
                                         final ExecutionSignalType signalType,
                                         final ExecutionStateType expectedExecutionStateType) {
        final List<ExecutionStateType> transitionsToState =
                EXECUTION_TRANSITION_CONTAINER.getTransitionsToState(currentStateType, signalType);

        Assertions.assertEquals(1, transitionsToState.size());
        Assertions.assertTrue(transitionsToState.contains(expectedExecutionStateType));
    }

    private void checkTransitionsToStateNotFound(final ExecutionStateType currentStateType,
                                                 final ExecutionSignalType signalType) {
        final List<ExecutionStateType> transitionsToState =
                EXECUTION_TRANSITION_CONTAINER.getTransitionsToState(currentStateType, signalType);

        Assertions.assertTrue(transitionsToState.isEmpty());
    }

}
