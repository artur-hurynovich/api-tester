package com.hurynovich.api_tester.converter.request_element_converter.impl;

import com.hurynovich.api_tester.converter.request_element_converter.RequestElementConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.enumeration.RequestElementType;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequestElementConverterImpl implements RequestElementConverter {

    @Override
    public MultiValueMap<String, String> convertToMultiValueMap(final @NonNull List<RequestElementDTO> elements) {
        if (!CollectionUtils.isEmpty(elements)) {
            final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

            elements.forEach(element ->
                    multiValueMap.put(element.getName(), Collections.singletonList(element.getValue())));

            return multiValueMap;
        } else {
            return new LinkedMultiValueMap<>();
        }
    }

    @Override
    public HttpHeaders convertToHttpHeaders(final @NonNull List<RequestElementDTO> elements) {
        if (!CollectionUtils.isEmpty(elements)) {
            final HttpHeaders httpHeaders = new HttpHeaders();

            elements.forEach(element -> httpHeaders.add(element.getName(), element.getValue()));

            return httpHeaders;
        } else {
            return new HttpHeaders();
        }
    }

    @Override
    public List<RequestElementDTO> convertToRequestElements(final @NonNull HttpHeaders httpHeaders) {
        final Set<Map.Entry<String, List<String>>> entries = httpHeaders.entrySet();

        return entries.stream().flatMap(entry ->
                entry.getValue().stream().map(value -> {
                    final RequestElementDTO requestElement = new RequestElementDTO();

                    requestElement.setName(entry.getKey());
                    requestElement.setValue(value);
                    requestElement.setType(RequestElementType.VALUE);

                    return requestElement;
                })).collect(Collectors.toList());
    }

}
