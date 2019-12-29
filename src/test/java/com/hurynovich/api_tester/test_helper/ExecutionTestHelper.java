package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
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

        final ExecutionStateCacheKey key = buildExecutionStateCacheKey();
        executionSignal.setKey(key);

        return executionSignal;
    }

    public static ExecutionStateCacheKey buildExecutionStateCacheKey() {
        final ExecutionStateCacheKey key = new ExecutionStateCacheKey();

        final long userId = RandomValueGenerator.generateRandomPositiveInt();
        key.setUserId(userId);

        final long requestChainId = RandomValueGenerator.generateRandomPositiveInt();
        key.setRequestChainId(requestChainId);

        return key;
    }

}
