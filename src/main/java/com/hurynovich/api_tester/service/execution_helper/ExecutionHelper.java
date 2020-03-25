package com.hurynovich.api_tester.service.execution_helper;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;

import java.util.List;

public interface ExecutionHelper {

    ExecutionState getExecutionState(ExecutionCacheKey key);

    ExecutionCacheKey initExecutionStateCache(RequestContainerDTO requestContainer);

    ExecutionState updateExecutionStateCache(ExecutionSignal executionSignal);

    ExecutionLogDTO getExecutionLog(ExecutionCacheKey key);

    List<String> resolveValidSignalNamesOnInit(ExecutionState executionState);

    List<String> resolveValidSignalNamesOnExecution(ExecutionState executionState);

}
