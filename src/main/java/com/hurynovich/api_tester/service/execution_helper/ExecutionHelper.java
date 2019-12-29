package com.hurynovich.api_tester.service.execution_helper;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;

import java.util.List;

public interface ExecutionHelper {

    ExecutionState getExecutionState(ExecutionStateCacheKey key);

    ExecutionState updateExecutionStateCache(ExecutionSignal executionSignal);

    ExecutionStateType resolveTransitionToExecutionStateType(ExecutionSignal executionSignal);

    List<ExecutionSignalType> resolveValidSignalTypesOnInit(ExecutionState executionState);

    List<ExecutionSignalType> resolveValidSignalTypesOnExecution(ExecutionState executionState);

}
