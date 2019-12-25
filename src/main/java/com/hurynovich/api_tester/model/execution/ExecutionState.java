package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;

import java.util.List;

public class ExecutionState {

    private List<RequestDTO> requests;

    private ExecutionStateType type;

    private List<ExecutionError> errors;

    public List<RequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(final List<RequestDTO> requests) {
        this.requests = requests;
    }

    public ExecutionStateType getType() {
        return type;
    }

    public void setType(final ExecutionStateType type) {
        this.type = type;
    }

    public List<ExecutionError> getErrors() {
        return errors;
    }

    public void setErrors(final List<ExecutionError> errors) {
        this.errors = errors;
    }

}
