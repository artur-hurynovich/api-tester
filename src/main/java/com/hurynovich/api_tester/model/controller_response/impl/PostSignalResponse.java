package com.hurynovich.api_tester.model.controller_response.impl;

import com.hurynovich.api_tester.model.controller_response.AbstractControllerResponse;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;

import java.util.List;

public class PostSignalResponse extends AbstractControllerResponse {

    private ExecutionStateType state;

    private List<ExecutionSignalType> types;

    public ExecutionStateType getState() {
        return state;
    }

    public void setState(ExecutionStateType state) {
        this.state = state;
    }

    public List<ExecutionSignalType> getTypes() {
        return types;
    }

    public void setTypes(List<ExecutionSignalType> types) {
        this.types = types;
    }

}
