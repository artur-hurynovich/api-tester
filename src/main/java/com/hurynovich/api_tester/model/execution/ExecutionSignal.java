package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.state_transition.has_signal.HasSignal;
import com.hurynovich.api_tester.state_transition.signal.Signal;

public class ExecutionSignal implements HasSignal {

    private Signal signal;

    private GenericExecutionCacheKey key;

    @Override
    public Signal getSignal() {
        return signal;
    }

    public void setSignal(final Signal signal) {
        this.signal = signal;
    }

    public GenericExecutionCacheKey getKey() {
        return key;
    }

    public void setKey(final GenericExecutionCacheKey key) {
        this.key = key;
    }

}
