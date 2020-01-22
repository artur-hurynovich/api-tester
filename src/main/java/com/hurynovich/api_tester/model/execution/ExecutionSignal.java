package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;

public class ExecutionSignal {

    private ExecutionSignalType type;

    private GenericExecutionCacheKey key;

    public ExecutionSignalType getType() {
        return type;
    }

    public void setType(final ExecutionSignalType type) {
        this.type = type;
    }

    public GenericExecutionCacheKey getKey() {
        return key;
    }

    public void setKey(final GenericExecutionCacheKey key) {
        this.key = key;
    }

}
