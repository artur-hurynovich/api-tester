package com.hurynovich.api_tester.client.exception;

public class ClientException extends Exception {

    public ClientException() {
        super();
    }

    public ClientException(final String message) {
        super(message);
    }

    public ClientException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ClientException(final Throwable cause) {
        super(cause);
    }

}
