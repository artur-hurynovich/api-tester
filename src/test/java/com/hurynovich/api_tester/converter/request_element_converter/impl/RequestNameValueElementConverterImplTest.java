package com.hurynovich.api_tester.converter.request_element_converter.impl;

import com.hurynovich.api_tester.converter.request_element_converter.RequestNameValueElementConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class RequestNameValueElementConverterImplTest {

    private final RequestNameValueElementConverter requestNameValueElementConverter = new RequestNameValueElementConverterImpl();

    @Test
    public void convertToMultiValueMapTest() {
        final List<NameValueElementDTO> parameters = RequestTestHelper.generateRandomNameValueElementDTOs(3);

        final MultiValueMap<String, String> multiValueMap = requestNameValueElementConverter.convertToMultiValueMap(parameters);

        checkMultiValueMap(parameters, multiValueMap);
    }

    @Test
    public void convertToHttpHeadersTest() {
        final List<NameValueElementDTO> headers = RequestTestHelper.generateRandomNameValueElementDTOs(3);

        final HttpHeaders httpHeaders = requestNameValueElementConverter.convertToHttpHeaders(headers);

        checkHttpHeaders(headers, httpHeaders);
    }

    @Test
    public void convertHttpHeadersToRequestElementsTest() {
        final HttpHeaders httpHeaders = RequestTestHelper.generateRandomHttpHeaders(3);

        final List<NameValueElementDTO> requestElements = requestNameValueElementConverter.convertToRequestElements(httpHeaders);

        checkHttpHeaders(requestElements, httpHeaders);
    }

    private void checkMultiValueMap(final List<NameValueElementDTO> parameters,
                                    final MultiValueMap<String, String> multiValueMap) {
        Assertions.assertEquals(parameters.size(), multiValueMap.size());

        parameters.forEach(parameter -> {
            final String name = parameter.getName();
            final String value = parameter.getValue();

            final List<String> values = multiValueMap.get(name);
            Assertions.assertNotNull(values);
            Assertions.assertEquals(1, values.size());

            Assertions.assertEquals(value, values.get(0));
        });
    }

    private void checkHttpHeaders(final List<NameValueElementDTO> requestElements,
                                  final HttpHeaders httpHeaders) {
        Assertions.assertEquals(requestElements.size(), httpHeaders.size());

        requestElements.forEach(parameter -> {
            final String name = parameter.getName();
            final String value = parameter.getValue();

            final List<String> values = httpHeaders.get(name);
            Assertions.assertNotNull(values);
            Assertions.assertEquals(1, values.size());

            Assertions.assertEquals(value, values.get(0));
        });
    }

}
