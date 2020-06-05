package com.hurynovich.api_tester.model.persistence.entity.impl;

import com.hurynovich.api_tester.model.persistence.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "APTE_ROLES")
public class UserRoleEntity extends AbstractEntity<Long> {

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(final List<UserEntity> users) {
        this.users = users;
    }

}
