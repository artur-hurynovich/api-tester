package com.hurynovich.api_tester.cache.impl;

import com.hurynovich.api_tester.cache.GenericCache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.execution.RequestExpressionUnitContainer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RequestExpressionUnitContainerCache extends GenericCache<ExecutionCacheKey, RequestExpressionUnitContainer> {

    public RequestExpressionUnitContainerCache() {
        super(1, TimeUnit.HOURS);
    }

}
