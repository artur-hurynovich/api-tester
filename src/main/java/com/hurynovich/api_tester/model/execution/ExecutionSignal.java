package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.state_transition.has_signal.HasSignal;
import com.hurynovich.api_tester.state_transition.signal.Signal;

public class ExecutionSignal implements HasSignal {

    private ExecutionStateCacheKey executionStateCacheKey;

    private Signal signal;

    public ExecutionStateCacheKey getExecutionStateCacheKey() {
        return executionStateCacheKey;
    }

    public void setExecutionStateCacheKey(final ExecutionStateCacheKey executionStateCacheKey) {
        this.executionStateCacheKey = executionStateCacheKey;
    }

    @Override
    public Signal getSignal() {
        return signal;
    }

    public void setSignal(final Signal signal) {
        this.signal = signal;
    }

}
