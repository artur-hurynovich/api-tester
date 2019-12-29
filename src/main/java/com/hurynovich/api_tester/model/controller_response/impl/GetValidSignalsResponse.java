package com.hurynovich.api_tester.model.controller_response.impl;

import com.hurynovich.api_tester.model.controller_response.AbstractControllerResponse;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;

import java.util.List;

public class GetValidSignalsResponse extends AbstractControllerResponse {

    private List<ExecutionSignalType> types;

    public List<ExecutionSignalType> getTypes() {
        return types;
    }

    public void setTypes(List<ExecutionSignalType> types) {
        this.types = types;
    }

}
