package com.hurynovich.api_tester.model.persistence.document;

import com.hurynovich.api_tester.model.persistence.Identified;

import javax.persistence.Id;
import java.io.Serializable;

public class AbstractDocument<I extends Serializable> implements Identified<I> {

    @Id
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
