package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.enumeration.RequestExecutionSignalType;

import java.util.UUID;

public class RequestExecutionSignal {

	private RequestExecutionSignalType type;

	private UUID requestExecutionStateId;

	private Long requestChainId;

	public RequestExecutionSignalType getType() {
		return type;
	}

	public void setType(final RequestExecutionSignalType type) {
		this.type = type;
	}

	public UUID getRequestExecutionStateId() {
		return requestExecutionStateId;
	}

	public void setRequestExecutionStateId(final UUID requestExecutionStateId) {
		this.requestExecutionStateId = requestExecutionStateId;
	}

	public Long getRequestChainId() {
		return requestChainId;
	}

	public void setRequestChainId(final Long requestChainId) {
		this.requestChainId = requestChainId;
	}

}
