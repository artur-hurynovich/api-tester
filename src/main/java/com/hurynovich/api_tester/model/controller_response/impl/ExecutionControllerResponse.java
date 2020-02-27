package com.hurynovich.api_tester.model.controller_response.impl;

import com.hurynovich.api_tester.model.controller_response.AbstractControllerResponse;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;

import java.util.List;

public class ExecutionControllerResponse extends AbstractControllerResponse {

    private ExecutionStateType state;

    private List<ExecutionSignalType> validSignals;

    public ExecutionStateType getState() {
        return state;
    }

    public void setState(ExecutionStateType state) {
        this.state = state;
    }

    public List<ExecutionSignalType> getValidSignals() {
        return validSignals;
    }

    public void setValidSignals(List<ExecutionSignalType> validSignals) {
        this.validSignals = validSignals;
    }

}
