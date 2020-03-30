package com.hurynovich.api_tester.cache.cache_key.impl;

import com.hurynovich.api_tester.cache.cache_key.CacheKey;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class ExecutionCacheKey implements CacheKey {

    private final String executionKey;

    public ExecutionCacheKey(final @NonNull String executionKey) {
        this.executionKey = executionKey;
    }

    public String getExecutionKey() {
        return executionKey;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExecutionCacheKey that = (ExecutionCacheKey) o;

        return Objects.equals(executionKey, that.executionKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(executionKey);
    }

}
