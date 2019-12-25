package com.hurynovich.api_tester.service.executor;

import com.hurynovich.api_tester.model.execution.ExecutionResult;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;

public interface Executor {

    ExecutionResult execute(ExecutionSignal signal);

}
