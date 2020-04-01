package com.hurynovich.api_tester.model.persistence.entity;

import com.hurynovich.api_tester.model.persistence.Identified;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class AbstractEntity<I extends Serializable> implements Identified<I> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
