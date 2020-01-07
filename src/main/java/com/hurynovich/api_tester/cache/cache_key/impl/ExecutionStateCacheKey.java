package com.hurynovich.api_tester.cache.cache_key.impl;

import com.hurynovich.api_tester.cache.cache_key.CacheKey;
import com.hurynovich.api_tester.utils.ObjectUtils;

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

        final ExecutionStateCacheKey that = (ExecutionStateCacheKey) o;
        return ObjectUtils.EqualsChecker.getInstance().
                with(userId, that.getUserId()).
                with(requestChainId, that.getRequestChainId()).
                check();
    }

    @Override
    public int hashCode() {
        return ObjectUtils.HashCodeCalculator.getInstance().
                with(userId).
                with(requestChainId).
                calculate();
    }

}
