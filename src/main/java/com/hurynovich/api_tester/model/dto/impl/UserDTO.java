package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

import java.util.List;

public class UserDTO extends AbstractDTO<Long> {

    private String email;

    private String password;

    private List<UserRoleDTO> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<UserRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(final List<UserRoleDTO> roles) {
        this.roles = roles;
    }

}
