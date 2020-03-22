package com.hurynovich.api_tester.model.controller_response.impl;

import com.hurynovich.api_tester.model.controller_response.AbstractControllerResponse;

import java.util.List;

public class ExecutionControllerResponse extends AbstractControllerResponse {

    private String executionKey;

    private String stateName;

    private List<String> validSignalNames;

    public String getExecutionKey() {
        return executionKey;
    }

    public void setExecutionKey(final String executionKey) {
        this.executionKey = executionKey;
    }

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

}
