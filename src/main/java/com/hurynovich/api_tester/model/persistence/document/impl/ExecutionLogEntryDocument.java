package com.hurynovich.api_tester.model.persistence.document.impl;

import com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType;
import com.hurynovich.api_tester.model.persistence.document.AbstractDocument;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ExecutionLogEntryDocument extends AbstractDocument<String> {

    private ExecutionLogEntryType type;

    private LocalDateTime dateTime;

    private HttpMethod method;

    private List<NameValueElementDocument> headers;

    private List<NameValueElementDocument> parameters;

    private String url;

    private HttpStatus httpStatus;

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

    public List<NameValueElementDocument> getHeaders() {
        return headers;
    }

    public void setHeaders(final List<NameValueElementDocument> headers) {
        this.headers = headers;
    }

    public List<NameValueElementDocument> getParameters() {
        return parameters;
    }

    public void setParameters(final List<NameValueElementDocument> parameters) {
        this.parameters = parameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
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
