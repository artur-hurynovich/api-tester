package com.hurynovich.api_tester.service.execution_helper;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;

import java.util.List;

public interface ExecutionHelper {

    ExecutionState getExecutionState(ExecutionStateCacheKey key);

    ExecutionStateCacheKey initExecutionStateCache(RequestContainerDTO requestContainer);

    ExecutionState updateExecutionStateCache(ExecutionSignal executionSignal);

    ExecutionLogDTO getExecutionLog(ExecutionStateCacheKey key);

    List<String> resolveValidSignalNamesOnInit(ExecutionState executionState);

    List<String> resolveValidSignalNamesOnExecution(ExecutionState executionState);

}
