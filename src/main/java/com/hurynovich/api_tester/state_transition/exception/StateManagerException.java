package com.hurynovich.api_tester.state_transition.exception;

public class StateManagerException extends RuntimeException {

    public StateManagerException() {
        super();
    }

    public StateManagerException(final String s) {
        super(s);
    }

    public StateManagerException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public StateManagerException(final Throwable throwable) {
        super(throwable);
    }

}
