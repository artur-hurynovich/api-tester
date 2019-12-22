package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class ResponseDTO extends AbstractDTO {

	private HttpStatus status;

	private HttpHeaders headers;

	private String body;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(final HttpStatus status) {
		this.status = status;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(final HttpHeaders headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

}
