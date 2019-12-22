package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.enumeration.RequestExecutionErrorType;

public class RequestExecutionError {

	private RequestExecutionErrorType type;

	private String description;

	public RequestExecutionErrorType getType() {
		return type;
	}

	public void setType(final RequestExecutionErrorType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
