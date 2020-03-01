package com.hurynovich.api_tester.state_transition.exception;

public class StateException extends RuntimeException {

    public StateException() {
        super();
    }

    public StateException(final String s) {
        super(s);
    }

    public StateException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public StateException(final Throwable throwable) {
        super(throwable);
    }

}
