package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;

public class RequestDTO extends AbstractDTO {

    private HttpMethod method;

    private HttpHeaders headers;

    private String url;

    private List<RequestParameterDTO> parameters;

    private String body;

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

    public List<RequestParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(final List<RequestParameterDTO> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
