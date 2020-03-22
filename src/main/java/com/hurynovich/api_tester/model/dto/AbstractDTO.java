package com.hurynovich.api_tester.model.dto;

import com.hurynovich.api_tester.model.persistence.Identified;

import java.io.Serializable;

public class AbstractDTO<I extends Serializable> implements Identified<I> {

    private I id;

    @Override
    public I getId() {
        return id;
    }

    @Override
    public void setId(final I id) {
        this.id = id;
    }

}
