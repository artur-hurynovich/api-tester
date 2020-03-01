package com.hurynovich.api_tester.model.entity.impl;

import com.hurynovich.api_tester.model.entity.AbstractEntity;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apte_requests")
public class RequestEntity extends AbstractEntity {

    private HttpMethod method;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "request_id")
    private List<NameValueElementEntity> headers;

    private String url;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "request_id")
    private List<NameValueElementEntity> parameters;

    private String body;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(final HttpMethod method) {
        this.method = method;
    }

    public List<NameValueElementEntity> getHeaders() {
        return headers;
    }

    public void setHeaders(final List<NameValueElementEntity> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public List<NameValueElementEntity> getParameters() {
        return parameters;
    }

    public void setParameters(final List<NameValueElementEntity> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
