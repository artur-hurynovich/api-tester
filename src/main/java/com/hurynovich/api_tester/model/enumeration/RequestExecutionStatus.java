package com.hurynovich.api_tester.model.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum RequestExecutionStatus {

	READY(Collections.singletonList(RequestExecutionSignalType.START)),
	RUNNING(Arrays.asList(RequestExecutionSignalType.PAUSE, RequestExecutionSignalType.STOP)),
	PAUSED(Arrays.asList(RequestExecutionSignalType.RESUME, RequestExecutionSignalType.STOP)),
	STOPPED(Collections.singletonList(RequestExecutionSignalType.START)),
	FINISHED(Collections.singletonList(RequestExecutionSignalType.START)),
	ERROR(Collections.singletonList(RequestExecutionSignalType.START));

	List<RequestExecutionSignalType> validSignalTypes;

	RequestExecutionStatus(final List<RequestExecutionSignalType> validSignalTypes) {
		this.validSignalTypes = validSignalTypes;
	}

	public List<RequestExecutionSignalType> getValidSignalTypes() {
		return validSignalTypes;
	}

}
