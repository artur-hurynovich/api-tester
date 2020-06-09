package com.hurynovich.api_tester.service.request_expression_manager;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;

import java.util.List;

public interface RequestExpressionManager {

    void initRequestExpressions(ExecutionCacheKey executionCacheKey, List<RequestDTO> requests);

    void applyRequestExpression(ExecutionCacheKey executionCacheKey, RequestDTO request);

    void evaluateRequestExpression(ExecutionCacheKey executionCacheKey, int index, ResponseDTO response);

    void invalidateRequestExpressions(ExecutionCacheKey executionCacheKey);

}
