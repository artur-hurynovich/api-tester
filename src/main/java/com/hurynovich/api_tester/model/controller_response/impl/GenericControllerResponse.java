package com.hurynovich.api_tester.model.controller_response.impl;

import com.hurynovich.api_tester.model.controller_response.AbstractControllerResponse;

public class GenericControllerResponse<T> extends AbstractControllerResponse {

    private T payload;

    public T getPayload() {
        return payload;
    }

    public void setPayload(final T payload) {
        this.payload = payload;
    }

}
