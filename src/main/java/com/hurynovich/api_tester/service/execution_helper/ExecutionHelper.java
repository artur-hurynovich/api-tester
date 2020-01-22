package com.hurynovich.api_tester.service.execution_helper;

import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;

import java.util.List;

public interface ExecutionHelper {

    ExecutionState getExecutionState(GenericExecutionCacheKey key);

    ExecutionState updateExecutionStateCache(ExecutionSignal executionSignal);

    ExecutionLogDTO getExecutionLog(GenericExecutionCacheKey key);

    ExecutionStateType resolveTransitionToExecutionStateType(ExecutionSignal executionSignal);

    List<ExecutionSignalType> resolveValidSignalTypesOnInit(ExecutionState executionState);

    List<ExecutionSignalType> resolveValidSignalTypesOnExecution(ExecutionState executionState);

}
