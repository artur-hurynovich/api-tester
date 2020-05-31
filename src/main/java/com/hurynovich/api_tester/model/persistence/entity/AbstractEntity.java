package com.hurynovich.api_tester.model.persistence.entity;

import com.hurynovich.api_tester.model.enumeration.Status;
import com.hurynovich.api_tester.model.persistence.PersistentObject;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class AbstractEntity<I extends Serializable> implements PersistentObject<I> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private I id;

    @Column(name = "STATUS")
    @Enumerated
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
