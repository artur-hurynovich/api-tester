package com.hurynovich.api_tester.model.dto;

import com.hurynovich.api_tester.model.enumeration.Status;
import com.hurynovich.api_tester.model.persistence.PersistentObject;

import java.io.Serializable;

public class AbstractDTO<I extends Serializable> implements PersistentObject<I> {

    private I id;

    private Status status = Status.ACTIVE;

    @Override
    public I getId() {
        return id;
    }

    @Override
    public void setId(final I id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

}
