package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;

public class ExecutionSignal {

    private ExecutionSignalType type;

    private Long userId;

    private Long requestChainId;

    public ExecutionSignalType getType() {
        return type;
    }

    public void setType(final ExecutionSignalType type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getRequestChainId() {
        return requestChainId;
    }

    public void setRequestChainId(final Long requestChainId) {
        this.requestChainId = requestChainId;
    }

}
