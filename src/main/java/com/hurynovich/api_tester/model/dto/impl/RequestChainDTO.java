package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

import java.util.List;

public class RequestChainDTO extends AbstractDTO {

	private List<RequestDTO> requests;

	public List<RequestDTO> getRequests() {
		return requests;
	}

	public void setRequests(final List<RequestDTO> requests) {
		this.requests = requests;
	}

}
