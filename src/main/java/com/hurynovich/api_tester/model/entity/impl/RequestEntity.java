package com.hurynovich.api_tester.model.entity.impl;

import com.hurynovich.api_tester.model.entity.AbstractEntity;

import org.springframework.http.HttpMethod;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "apte_requests")
public class RequestEntity extends AbstractEntity {

    private HttpMethod method;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "request_id")
    private List<RequestElementEntity> headers;

    private String url;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "request_id")
    private List<RequestElementEntity> parameters;

    private String body;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(final HttpMethod method) {
        this.method = method;
    }

    public List<RequestElementEntity> getHeaders() {
        return headers;
    }

    public void setHeaders(final List<RequestElementEntity> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public List<RequestElementEntity> getParameters() {
        return parameters;
    }

    public void setParameters(final List<RequestElementEntity> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
