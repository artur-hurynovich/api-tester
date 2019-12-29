package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;

public class ExecutionSignal {

    private ExecutionSignalType type;

    private ExecutionStateCacheKey key;

    public ExecutionSignalType getType() {
        return type;
    }

    public void setType(final ExecutionSignalType type) {
        this.type = type;
    }

    public ExecutionStateCacheKey getKey() {
        return key;
    }

    public void setKey(final ExecutionStateCacheKey key) {
        this.key = key;
    }

}
