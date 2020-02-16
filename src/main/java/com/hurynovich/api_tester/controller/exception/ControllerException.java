package com.hurynovich.api_tester.controller.exception;

public class ControllerException extends RuntimeException{

    public ControllerException() {
        super();
    }

    public ControllerException(final String message) {
        super(message);
    }

    public ControllerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ControllerException(final Throwable cause) {
        super(cause);
    }

}
