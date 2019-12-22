package com.hurynovich.api_tester.service.request_executor;

import com.hurynovich.api_tester.model.execution.RequestExecutionResult;
import com.hurynovich.api_tester.model.execution.RequestExecutionSignal;

public interface RequestExecutor {

	RequestExecutionResult execute(RequestExecutionSignal signal);

}
