package com.hurynovich.api_tester.service.exception;

public class RequestExpressionManagerException extends RuntimeException {

    public RequestExpressionManagerException() {
        super();
    }

    public RequestExpressionManagerException(final String message) {
        super(message);
    }

    public RequestExpressionManagerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RequestExpressionManagerException(final Throwable cause) {
        super(cause);
    }

}
