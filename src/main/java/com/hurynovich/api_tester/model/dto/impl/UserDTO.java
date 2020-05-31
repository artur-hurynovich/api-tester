package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

public class UserDTO extends AbstractDTO<Long> {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

}
