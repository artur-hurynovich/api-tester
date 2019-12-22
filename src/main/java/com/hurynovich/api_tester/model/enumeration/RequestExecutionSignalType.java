package com.hurynovich.api_tester.model.enumeration;

public enum RequestExecutionSignalType {

	START(RequestExecutionStatus.RUNNING),
	PAUSE(RequestExecutionStatus.PAUSED),
	RESUME(RequestExecutionStatus.RUNNING),
	STOP(RequestExecutionStatus.STOPPED);

	RequestExecutionStatus transitionToStatus;

	RequestExecutionSignalType(final RequestExecutionStatus transitionToStatus) {
		this.transitionToStatus = transitionToStatus;
	}

	public RequestExecutionStatus getTransitionToStatus() {
		return transitionToStatus;
	}

}
