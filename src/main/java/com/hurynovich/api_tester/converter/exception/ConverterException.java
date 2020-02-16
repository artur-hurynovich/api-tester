package com.hurynovich.api_tester.converter.exception;

public class ConverterException extends RuntimeException {

    public ConverterException() {
        super();
    }

    public ConverterException(final String message) {
        super(message);
    }

    public ConverterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ConverterException(final Throwable cause) {
        super(cause);
    }

}
