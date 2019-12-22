package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.enumeration.RequestExecutionStatus;

import java.util.List;
import java.util.UUID;

public class RequestExecutionState {

	private UUID id;

	private Long requestExecutionChainId;

	private RequestExecutionStatus status;

	private List<RequestExecutionError> errors;

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public Long getRequestExecutionChainId() {
		return requestExecutionChainId;
	}

	public void setRequestExecutionChainId(final Long requestExecutionChainId) {
		this.requestExecutionChainId = requestExecutionChainId;
	}

	public RequestExecutionStatus getStatus() {
		return status;
	}

	public void setStatus(final RequestExecutionStatus status) {
		this.status = status;
	}

	public List<RequestExecutionError> getErrors() {
		return errors;
	}

	public void setErrors(final List<RequestExecutionError> errors) {
		this.errors = errors;
	}

}
