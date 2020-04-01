package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

public class UserDTO extends AbstractDTO<Long> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
