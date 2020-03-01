package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseDTO extends AbstractDTO {

    private HttpStatus status;

    private List<NameValueElementDTO> headers;

    private String body;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public List<NameValueElementDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(final List<NameValueElementDTO> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
