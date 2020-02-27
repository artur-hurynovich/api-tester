package com.hurynovich.api_tester.model.entity.impl;

import com.hurynovich.api_tester.model.entity.AbstractEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "apte_request_chains")
public class RequestChainEntity extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "request_chain_id")
    private List<RequestEntity> requests;

    public List<RequestEntity> getRequests() {
        return requests;
    }

    public void setRequests(final List<RequestEntity> requests) {
        this.requests = requests;
    }

}
