package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

import java.util.List;

public class RequestContainerDTO extends AbstractDTO<String> {

    private String name;

    private String description;

    private List<RequestDTO> requests;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<RequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(final List<RequestDTO> requests) {
        this.requests = requests;
    }

}
