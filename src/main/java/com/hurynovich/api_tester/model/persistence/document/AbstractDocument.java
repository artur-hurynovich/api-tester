package com.hurynovich.api_tester.model.persistence.document;

import com.hurynovich.api_tester.model.enumeration.Status;
import com.hurynovich.api_tester.model.persistence.PersistentObject;

import javax.persistence.Id;
import java.io.Serializable;

public class AbstractDocument<I extends Serializable> implements PersistentObject<I> {

    @Id
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
