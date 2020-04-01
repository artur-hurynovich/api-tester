package com.hurynovich.api_tester.cache.impl;

import com.hurynovich.api_tester.cache.GenericCache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ExecutionStateCache extends GenericCache<ExecutionCacheKey, ExecutionState> {

    public ExecutionStateCache() {
        super(15, TimeUnit.MINUTES);
    }

}
