package com.hurynovich.api_tester.model.persistence.document;

import com.hurynovich.api_tester.model.persistence.Identified;

import javax.persistence.Id;

public class AbstractDocument implements Identified<String> {

    @Id
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }

}
