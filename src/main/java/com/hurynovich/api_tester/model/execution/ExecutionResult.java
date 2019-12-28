package com.hurynovich.api_tester.model.execution;

import java.util.ArrayList;
import java.util.List;

public class ExecutionResult {

    private ExecutionState executionState;

    private final List<ExecutionLogEntry> executionLogEntries = new ArrayList<>();

    public ExecutionState getExecutionState() {
        return executionState;
    }

    public void setExecutionState(final ExecutionState executionState) {
        this.executionState = executionState;
    }

    public List<ExecutionLogEntry> getExecutionLogEntries() {
        return executionLogEntries;
    }

    public void addExecutionLogEntry(final ExecutionLogEntry executionLogEntry) {
        executionLogEntries.add(executionLogEntry);
    }

}
