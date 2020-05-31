package com.hurynovich.api_tester.model.persistence.document.impl;

import com.hurynovich.api_tester.model.persistence.document.AbstractDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "APTE_REQUEST_CONTAINERS")
public class RequestContainerDocument extends AbstractDocument<String> {

    private String name;

    private String description;

    private List<RequestDocument> requests;

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

    public List<RequestDocument> getRequests() {
        return requests;
    }

    public void setRequests(final List<RequestDocument> requests) {
        this.requests = requests;
    }

}
