package com.hurynovich.api_tester.cache.impl;

import com.hurynovich.api_tester.cache.GenericCache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import org.springframework.stereotype.Component;

@Component
public class ExecutionLogCache extends GenericCache<ExecutionCacheKey, ExecutionLogDTO> {

}
