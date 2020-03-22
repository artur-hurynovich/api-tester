package com.hurynovich.api_tester.model.persistence.document.impl;

import com.hurynovich.api_tester.model.persistence.document.AbstractDocument;
import org.springframework.http.HttpMethod;

import java.util.List;

public class RequestDocument extends AbstractDocument {

    private HttpMethod method;

    private List<NameValueElementDocument> headers;

    private String url;

    private List<NameValueElementDocument> parameters;

    private String body;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public List<NameValueElementDocument> getParameters() {
        return parameters;
    }

    public void setParameters(final List<NameValueElementDocument> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
