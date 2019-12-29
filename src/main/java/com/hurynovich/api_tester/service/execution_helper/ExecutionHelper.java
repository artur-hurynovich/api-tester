package com.hurynovich.api_tester.service.execution_helper;

import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;

public interface ExecutionHelper {

    ExecutionState updateExecutionStateCache(ExecutionSignal executionSignal);

    ExecutionStateType resolveTransitionToExecutionStateType(ExecutionSignal executionSignal);

}
