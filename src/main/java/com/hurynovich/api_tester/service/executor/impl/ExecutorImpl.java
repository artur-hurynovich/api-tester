package com.hurynovich.api_tester.service.executor.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.client.Client;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.execution.ExecutionResult;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.executor.Executor;

public class ExecutorImpl implements Executor {

    private final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache;

    private final DTOService<RequestChainDTO, Long> requestChainService;

    private final Client client;

    public ExecutorImpl(final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache,
                        final DTOService<RequestChainDTO, Long> requestChainService,
                        final Client client) {
        this.executionStateCache = executionStateCache;
        this.requestChainService = requestChainService;
        this.client = client;
    }

    @Override
    public ExecutionResult execute(final ExecutionSignal signal) {

        return null;
    }

}
