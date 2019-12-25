package com.hurynovich.api_tester.service.executor;

import com.hurynovich.api_tester.model.execution.ExecutionSignal;

public interface Executor {

    void execute(ExecutionSignal signal);

}
