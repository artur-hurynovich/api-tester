package com.hurynovich.api_tester.cache.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.execution.ExecutionState;

public class ExecutionStateCache implements Cache<ExecutionStateCacheKey, ExecutionState> {

    @Override
    public ExecutionState get(final ExecutionStateCacheKey key) {
        return null;
    }

}
