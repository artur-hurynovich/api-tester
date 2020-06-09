package com.hurynovich.api_tester.utils;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.execution.IndexedRequestDTOWrapper;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestWrapperUtils {

    private RequestWrapperUtils() {

    }

    public static List<IndexedRequestDTOWrapper> wrapRequests(final @NonNull List<RequestDTO> requests) {
        final List<IndexedRequestDTOWrapper> requestWrappers = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            requestWrappers.add(new IndexedRequestDTOWrapper(i, requests.get(i)));
        }

        return requestWrappers;
    }

    public static List<RequestDTO> unwrapRequests(final @NonNull List<IndexedRequestDTOWrapper> requestsWrappers) {
        return requestsWrappers.stream().
                map(IndexedRequestDTOWrapper::getRequest).
                collect(Collectors.toList());
    }

}
