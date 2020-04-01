package com.hurynovich.api_tester.model.persistence.entity.impl;

import com.hurynovich.api_tester.model.persistence.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "apte_users")
public class UserEntity extends AbstractEntity<Long> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
