package com.hurynovich.api_tester.model.persistence;

import com.hurynovich.api_tester.model.enumeration.Status;

import java.io.Serializable;

public interface PersistentObject<I extends Serializable> {

    I getId();

    void setId(I id);

    Status getStatus();

    void setStatus(Status status);

}
