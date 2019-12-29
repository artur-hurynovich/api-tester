package com.hurynovich.api_tester.model.enumeration;

public enum ExecutionSignalType {

    RUN,
    PAUSE,
    RESUME,
    STOP;

    public static ExecutionSignalType initialSignalType() {
        return RUN;
    }

}
