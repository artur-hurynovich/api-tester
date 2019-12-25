package com.hurynovich.api_tester.cache.cache_key.impl;

import com.hurynovich.api_tester.cache.cache_key.CacheKey;

import org.springframework.lang.NonNull;

public class ExecutionStateCacheKey implements CacheKey {

    private Long userId;

    private Long requestChainId;

    public ExecutionStateCacheKey(@NonNull final Long userId, @NonNull final Long requestChainId) {
        this.userId = userId;
        this.requestChainId = requestChainId;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;

        hashCode = 31 * hashCode + userId.hashCode();

        hashCode = 31 * hashCode + requestChainId.hashCode();

        return hashCode;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final ExecutionStateCacheKey temp = (ExecutionStateCacheKey) obj;
        return userId.equals(temp.userId) && requestChainId.equals(temp.requestChainId);
    }

}
