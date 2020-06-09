package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import org.springframework.lang.NonNull;

public class IndexedRequestDTOWrapper {

    private final Integer index;

    private final RequestDTO request;

    public IndexedRequestDTOWrapper(final @NonNull Integer index, final @NonNull RequestDTO request) {
        this.index = index;
        this.request = request;
    }

    public Integer getIndex() {
        return index;
    }

    public RequestDTO getRequest() {
        return request;
    }

}
