package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.enumeration.RequestParameterType;

public class RequestParameterDTO extends AbstractDTO {

	private RequestParameterType type;

	private String name;

	private String value;

	public RequestParameterType getType() {
		return type;
	}

	public void setType(final RequestParameterType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
