package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;

public class ExecutionResult {

    private ExecutionState executionState;

    private ExecutionLogDTO executionLog;

    public ExecutionState getExecutionState() {
        return executionState;
    }

    public void setExecutionState(final ExecutionState executionState) {
        this.executionState = executionState;
    }

    public ExecutionLogDTO getExecutionLog() {
        return executionLog;
    }

    public void setExecutionLog(final ExecutionLogDTO executionLog) {
        this.executionLog = executionLog;
    }

}
