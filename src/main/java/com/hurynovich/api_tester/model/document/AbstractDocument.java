package com.hurynovich.api_tester.model.document;

import javax.persistence.Id;

public class AbstractDocument {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

}
