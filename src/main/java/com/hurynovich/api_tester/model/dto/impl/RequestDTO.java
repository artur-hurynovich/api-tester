package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import org.springframework.http.HttpMethod;

import java.util.List;

public class RequestDTO extends AbstractDTO {

    private HttpMethod method;

    private List<GenericRequestElementDTO> headers;

    private String url;

    private List<GenericRequestElementDTO> parameters;

    private String body;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(final HttpMethod method) {
        this.method = method;
    }

    public List<GenericRequestElementDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(final List<GenericRequestElementDTO> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public List<GenericRequestElementDTO> getParameters() {
        return parameters;
    }

    public void setParameters(final List<GenericRequestElementDTO> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
