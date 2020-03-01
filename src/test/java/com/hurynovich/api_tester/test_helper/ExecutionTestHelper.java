package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;

public class ExecutionTestHelper {

    private ExecutionTestHelper() {

    }

    public static ExecutionState buildExecutionState(final ExecutionStateType type) {
        final ExecutionState executionState = new ExecutionState();

        executionState.setType(type);

        return executionState;
    }

    public static ExecutionSignal buildExecutionSignal(final ExecutionSignalType type) {
        final ExecutionSignal executionSignal = new ExecutionSignal();

        executionSignal.setType(type);

        final GenericExecutionCacheKey key = buildExecutionStateCacheKey();
        executionSignal.setKey(key);

        return executionSignal;
    }

    public static GenericExecutionCacheKey buildExecutionStateCacheKey() {
        final GenericExecutionCacheKey key = new GenericExecutionCacheKey();

        final long userId = RandomValueGenerator.generateRandomPositiveInt();
        key.setUserId(userId);

        final long requestChainId = RandomValueGenerator.generateRandomPositiveInt();
        key.setRequestChainId(requestChainId);

        return key;
    }

}
