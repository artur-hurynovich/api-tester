package com.hurynovich.api_tester.service.exception;

public class DTOServiceException extends RuntimeException {

    public DTOServiceException() {
        super();
    }

    public DTOServiceException(final String message) {
        super(message);
    }

    public DTOServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DTOServiceException(final Throwable cause) {
        super(cause);
    }

}
