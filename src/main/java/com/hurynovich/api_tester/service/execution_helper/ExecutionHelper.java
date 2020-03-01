package com.hurynovich.api_tester.service.execution_helper;

import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;

import java.util.List;

public interface ExecutionHelper {

    ExecutionState getExecutionState(GenericExecutionCacheKey key);

    ExecutionState updateExecutionStateCache(ExecutionSignal executionSignal);

    ExecutionLogDocument getExecutionLog(GenericExecutionCacheKey key);

    List<String> resolveValidSignalNamesOnInit(ExecutionState executionState);

    List<String> resolveValidSignalNamesOnExecution(ExecutionState executionState);

}
