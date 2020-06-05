package com.hurynovich.api_tester.model.controller_response.impl;

import com.hurynovich.api_tester.model.controller_response.AbstractControllerResponse;

public class AuthenticationControllerResponse extends AbstractControllerResponse {

    private String email;

    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

}
