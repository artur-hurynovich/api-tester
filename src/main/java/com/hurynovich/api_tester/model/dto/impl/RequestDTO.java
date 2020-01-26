package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import org.springframework.http.HttpMethod;

import java.util.List;

public class RequestDTO extends AbstractDTO {

    private HttpMethod method;

    private List<RequestElementDTO> headers;

    private String url;

    private List<RequestElementDTO> parameters;

    private String body;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(final HttpMethod method) {
        this.method = method;
    }

    public List<RequestElementDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(final List<RequestElementDTO> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public List<RequestElementDTO> getParameters() {
        return parameters;
    }

    public void setParameters(final List<RequestElementDTO> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
