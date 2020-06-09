package com.hurynovich.api_tester.service.exception;

public class RequestExpressionUnitContainerServiceException extends RuntimeException {

    public RequestExpressionUnitContainerServiceException() {
        super();
    }

    public RequestExpressionUnitContainerServiceException(final String message) {
        super(message);
    }

    public RequestExpressionUnitContainerServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RequestExpressionUnitContainerServiceException(final Throwable cause) {
        super(cause);
    }

}
