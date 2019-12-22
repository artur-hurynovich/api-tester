package com.hurynovich.api_tester.service.request_executor.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.client.Client;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.execution.RequestExecutionResult;
import com.hurynovich.api_tester.model.execution.RequestExecutionSignal;
import com.hurynovich.api_tester.model.execution.RequestExecutionState;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.request_executor.RequestExecutor;

public class RequestExecutorImpl implements RequestExecutor {

	private final Cache<Long, RequestExecutionState> requestExecutionStateCache;

	private final DTOService<RequestChainDTO, Long> requestChainService;

	private final Client client;

	public RequestExecutorImpl(final Cache<Long, RequestExecutionState> requestExecutionStateCache,
							   final DTOService<RequestChainDTO, Long> requestChainService,
							   final Client client) {
		this.requestExecutionStateCache = requestExecutionStateCache;
		this.requestChainService = requestChainService;
		this.client = client;
	}

	@Override
	public RequestExecutionResult execute(final RequestExecutionSignal signal) {

		return null;
	}

}
