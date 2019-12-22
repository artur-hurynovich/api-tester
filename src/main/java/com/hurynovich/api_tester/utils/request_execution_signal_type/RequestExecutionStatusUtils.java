package com.hurynovich.api_tester.utils.request_execution_signal_type;

import com.hurynovich.api_tester.model.enumeration.RequestExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.RequestExecutionStatus;

import java.util.EnumMap;
import java.util.Map;

public class RequestExecutionStatusUtils {

	private RequestExecutionStatusUtils() {
	}

	private static final Map<RequestExecutionSignalType, RequestExecutionStatus> TRANSITION_TO_STATUS_MAP =
		initTransitionToStatusMap();

	public static RequestExecutionStatus getTransitionToStatus(final RequestExecutionSignalType signalType) {
		return TRANSITION_TO_STATUS_MAP.get(signalType);
	}

	private static Map<RequestExecutionSignalType, RequestExecutionStatus> initTransitionToStatusMap() {
		final Map<RequestExecutionSignalType, RequestExecutionStatus> transitionToStatusMap =
			new EnumMap<>(RequestExecutionSignalType.class);

		transitionToStatusMap.put(RequestExecutionSignalType.START, RequestExecutionStatus.RUNNING);
		transitionToStatusMap.put(RequestExecutionSignalType.PAUSE, RequestExecutionStatus.PAUSED);
		transitionToStatusMap.put(RequestExecutionSignalType.RESUME, RequestExecutionStatus.RUNNING);
		transitionToStatusMap.put(RequestExecutionSignalType.STOP, RequestExecutionStatus.STOPPED);

		return transitionToStatusMap;
	}

}
