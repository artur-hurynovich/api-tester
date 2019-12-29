package com.hurynovich.api_tester.model.enumeration;

public enum ExecutionStateType {

    PENDING_RUNNING(true),
    RUNNING(false),
    PENDING_PAUSED(true),
    PAUSED(false),
    PENDING_STOPPED(true),
    STOPPED(false),
    FINISHED(false),
    ERROR(false);

    final boolean pendingState;

    ExecutionStateType(final boolean pendingState) {
        this.pendingState = pendingState;
    }

    public static ExecutionStateType errorStateType() {
        return ERROR;
    }

    public boolean isPendingState() {
        return pendingState;
    }

}
