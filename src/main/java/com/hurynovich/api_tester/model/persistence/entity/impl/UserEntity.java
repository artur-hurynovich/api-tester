package com.hurynovich.api_tester.model.persistence.entity.impl;

import com.hurynovich.api_tester.model.persistence.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "APTE_USERS")
public class UserEntity extends AbstractEntity<Long> {

    @Column(name = "LOGIN")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

}
