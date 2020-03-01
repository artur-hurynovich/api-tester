package com.hurynovich.api_tester.converter.request_element_converter.impl;

import com.hurynovich.api_tester.converter.request_element_converter.RequestElementConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
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
    public MultiValueMap<String, String> convertToMultiValueMap(final @NonNull List<NameValueElementDTO> elements) {
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
    public HttpHeaders convertToHttpHeaders(final @NonNull List<NameValueElementDTO> elements) {
        if (!CollectionUtils.isEmpty(elements)) {
            final HttpHeaders httpHeaders = new HttpHeaders();

            elements.forEach(element -> httpHeaders.add(element.getName(), element.getValue()));

            return httpHeaders;
        } else {
            return new HttpHeaders();
        }
    }

    @Override
    public List<NameValueElementDTO> convertToRequestElements(final @NonNull HttpHeaders httpHeaders) {
        final Set<Map.Entry<String, List<String>>> entries = httpHeaders.entrySet();

        return entries.stream().flatMap(entry ->
                entry.getValue().stream().map(value -> {
                    final NameValueElementDTO requestElement = new NameValueElementDTO();

                    requestElement.setName(entry.getKey());
                    requestElement.setValue(value);
                    requestElement.setType(NameValueElementType.VALUE);

                    return requestElement;
                })).collect(Collectors.toList());
    }

}
