package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExecutionLogEntry {

    private ExecutionLogEntryType type;

    private LocalDateTime dateTime;

    private HttpMethod method;

    private HttpHeaders headers;

    private String url;

    private HttpStatus status;

    private String body;

    private String errorMessage;

    public ExecutionLogEntryType getType() {
        return type;
    }

    public void setType(final ExecutionLogEntryType type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(final HttpMethod method) {
        this.method = method;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(final HttpHeaders headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
