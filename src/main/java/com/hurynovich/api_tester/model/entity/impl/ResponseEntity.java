package com.hurynovich.api_tester.model.entity.impl;

import com.hurynovich.api_tester.model.entity.AbstractEntity;

import org.springframework.http.HttpStatus;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "apte_responses")
public class ResponseEntity extends AbstractEntity {

    private HttpStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "response_id")
    private List<NameValueElementEntity> headers;

    private String body;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public List<NameValueElementEntity> getHeaders() {
        return headers;
    }

    public void setHeaders(final List<NameValueElementEntity> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
