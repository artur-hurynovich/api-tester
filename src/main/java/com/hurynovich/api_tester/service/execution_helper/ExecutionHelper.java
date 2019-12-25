package com.hurynovich.api_tester.service.execution_helper;

import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;

import java.util.List;

public interface ExecutionHelper {

    List<ExecutionSignalType> resolveValidExecutionSignalTypes(ExecutionState executionState);

    ExecutionStateType resolveTransitionToExecutionStateType(ExecutionSignal executionSignal);

}
