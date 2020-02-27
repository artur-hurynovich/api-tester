package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;

import java.util.List;

public class ExecutionResult {

    private ExecutionState executionState;

    private List<ExecutionSignalType> validSignals;

    private ExecutionLogDocument executionLog;

    public ExecutionState getExecutionState() {
        return executionState;
    }

    public void setExecutionState(final ExecutionState executionState) {
        this.executionState = executionState;
    }

    public List<ExecutionSignalType> getValidSignals() {
        return validSignals;
    }

    public void setValidSignals(List<ExecutionSignalType> validSignals) {
        this.validSignals = validSignals;
    }

    public ExecutionLogDocument getExecutionLog() {
        return executionLog;
    }

    public void setExecutionLog(final ExecutionLogDocument executionLog) {
        this.executionLog = executionLog;
    }

}
