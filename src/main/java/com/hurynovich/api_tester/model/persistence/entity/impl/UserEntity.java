package com.hurynovich.api_tester.model.persistence.entity.impl;

import com.hurynovich.api_tester.model.persistence.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "apte_users")
public class UserEntity extends AbstractEntity<Long> {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

}
