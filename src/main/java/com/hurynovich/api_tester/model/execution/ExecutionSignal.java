package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.state_transition.has_signal.HasSignal;
import com.hurynovich.api_tester.state_transition.signal.Signal;

public class ExecutionSignal implements HasSignal {

    private ExecutionCacheKey executionCacheKey;

    private Signal signal;

    public ExecutionCacheKey getExecutionCacheKey() {
        return executionCacheKey;
    }

    public void setExecutionCacheKey(final ExecutionCacheKey executionCacheKey) {
        this.executionCacheKey = executionCacheKey;
    }

    @Override
    public Signal getSignal() {
        return signal;
    }

    public void setSignal(final Signal signal) {
        this.signal = signal;
    }

}
