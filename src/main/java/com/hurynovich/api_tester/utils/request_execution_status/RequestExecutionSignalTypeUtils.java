package com.hurynovich.api_tester.utils.request_execution_status;

import com.hurynovich.api_tester.model.enumeration.RequestExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.RequestExecutionStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class RequestExecutionSignalTypeUtils {

	private RequestExecutionSignalTypeUtils() {
	}

	private static final Map<RequestExecutionStatus, List<RequestExecutionSignalType>> VALID_SIGNAL_TYPES =
		initValidSignalTypes();

	public static List<RequestExecutionSignalType> getValidSignalTypes(final RequestExecutionStatus status) {
		return VALID_SIGNAL_TYPES.getOrDefault(status, Collections.emptyList());
	}

	private static Map<RequestExecutionStatus, List<RequestExecutionSignalType>> initValidSignalTypes() {
		final Map<RequestExecutionStatus, List<RequestExecutionSignalType>> transitionToStatusMap =
			new EnumMap<>(RequestExecutionStatus.class);

		transitionToStatusMap
			.put(RequestExecutionStatus.READY, Collections.singletonList(RequestExecutionSignalType.START));
		transitionToStatusMap.put(RequestExecutionStatus.RUNNING,
								  Arrays.asList(RequestExecutionSignalType.PAUSE, RequestExecutionSignalType.STOP));
		transitionToStatusMap.put(RequestExecutionStatus.PAUSED,
								  Arrays.asList(RequestExecutionSignalType.START, RequestExecutionSignalType.RESUME,
												RequestExecutionSignalType.STOP));
		transitionToStatusMap
			.put(RequestExecutionStatus.STOPPED, Collections.singletonList(RequestExecutionSignalType.START));
		transitionToStatusMap
			.put(RequestExecutionStatus.FINISHED, Collections.singletonList(RequestExecutionSignalType.START));
		transitionToStatusMap
			.put(RequestExecutionStatus.ERROR, Collections.singletonList(RequestExecutionSignalType.START));

		return transitionToStatusMap;
	}

}
