package com.hurynovich.api_tester.cache.impl;

import com.hurynovich.api_tester.cache.GenericCache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ExecutionLogCache extends GenericCache<ExecutionCacheKey, ExecutionLogDTO> {

    public ExecutionLogCache() {
        super(15, TimeUnit.MINUTES);
    }

}
