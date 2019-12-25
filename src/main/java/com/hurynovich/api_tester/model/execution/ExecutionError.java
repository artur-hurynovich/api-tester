package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.enumeration.ExecutionErrorType;

public class ExecutionError {

    private ExecutionErrorType type;

    private String description;

    public ExecutionErrorType getType() {
        return type;
    }

    public void setType(final ExecutionErrorType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
