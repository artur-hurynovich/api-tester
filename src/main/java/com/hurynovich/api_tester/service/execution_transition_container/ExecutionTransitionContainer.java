package com.hurynovich.api_tester.service.execution_transition_container;

import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;

import java.util.List;

public interface ExecutionTransitionContainer {

    List<ExecutionSignalType> getValidSignalTypesForState(ExecutionStateType stateType);

    List<ExecutionStateType> getTransitionsToState(ExecutionStateType currentStateType, ExecutionSignalType signalType);

}
