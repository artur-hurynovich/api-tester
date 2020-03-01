package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;

import java.util.List;

public class ExecutionResult {

    private String stateName;

    private List<String> validSignalNames;

    private ExecutionLogDocument executionLog;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(final String stateName) {
        this.stateName = stateName;
    }

    public List<String> getValidSignalNames() {
        return validSignalNames;
    }

    public void setValidSignalNames(final List<String> validSignalNames) {
        this.validSignalNames = validSignalNames;
    }

    public ExecutionLogDocument getExecutionLog() {
        return executionLog;
    }

    public void setExecutionLog(final ExecutionLogDocument executionLog) {
        this.executionLog = executionLog;
    }

}
