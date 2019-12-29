package com.hurynovich.api_tester.cache.cache_key.impl;

import com.hurynovich.api_tester.cache.cache_key.CacheKey;

import java.util.Objects;

public class ExecutionStateCacheKey implements CacheKey {

    private Long userId;

    private Long requestChainId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getRequestChainId() {
        return requestChainId;
    }

    public void setRequestChainId(final Long requestChainId) {
        this.requestChainId = requestChainId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExecutionStateCacheKey key = (ExecutionStateCacheKey) o;

        return userId.equals(key.userId) &&
                requestChainId.equals(key.requestChainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, requestChainId);
    }

}
