package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;

import java.util.List;

public class ExecutionResult {

    private String stateName;

    private List<String> validSignalNames;

    private ExecutionLogDTO executionLog;

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

    public ExecutionLogDTO getExecutionLog() {
        return executionLog;
    }

    public void setExecutionLog(final ExecutionLogDTO executionLog) {
        this.executionLog = executionLog;
    }

}
