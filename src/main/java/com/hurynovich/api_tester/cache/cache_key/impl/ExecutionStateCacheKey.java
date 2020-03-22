package com.hurynovich.api_tester.cache.cache_key.impl;

import com.hurynovich.api_tester.cache.cache_key.CacheKey;
import org.springframework.lang.NonNull;

public class ExecutionStateCacheKey implements CacheKey {

    private final String executionKey;

    public ExecutionStateCacheKey(final @NonNull String executionKey) {
        this.executionKey = executionKey;
    }

    public String getExecutionKey() {
        return executionKey;
    }

}
