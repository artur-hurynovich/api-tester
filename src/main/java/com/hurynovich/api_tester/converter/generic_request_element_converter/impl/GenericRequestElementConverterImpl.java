package com.hurynovich.api_tester.converter.generic_request_element_converter.impl;

import com.hurynovich.api_tester.converter.generic_request_element_converter.GenericRequestElementConverter;
import com.hurynovich.api_tester.model.dto.impl.GenericRequestElementDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;

@Service
public class GenericRequestElementConverterImpl implements GenericRequestElementConverter {

    @Override
    public MultiValueMap<String, String> convertToMultiValueMap(final @NonNull List<GenericRequestElementDTO> elements) {
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
    public HttpHeaders convertToHttpHeaders(final @NonNull List<GenericRequestElementDTO> elements) {
        if (!CollectionUtils.isEmpty(elements)) {
            final HttpHeaders httpHeaders = new HttpHeaders();

            elements.forEach(element -> httpHeaders.add(element.getName(), element.getValue()));

            return httpHeaders;
        } else {
            return new HttpHeaders();
        }
    }

}
