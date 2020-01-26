package com.hurynovich.api_tester.converter.generic_request_elent_converter.impl;

import com.hurynovich.api_tester.converter.generic_request_element_converter.RequestElementConverter;
import com.hurynovich.api_tester.converter.generic_request_element_converter.impl.RequestElementConverterImpl;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class RequestElementConverterImplTest {

    private final RequestElementConverter requestElementConverter = new RequestElementConverterImpl();

    @Test
    public void convertToMultiValueMapTest() {
        final List<RequestElementDTO> parameters = RequestTestHelper.generateRandomRequestElements(3);

        final MultiValueMap<String, String> multiValueMap = requestElementConverter.convertToMultiValueMap(parameters);

        checkMultiValueMap(parameters, multiValueMap);
    }

    @Test
    public void convertToHttpHeadersTest() {
        final List<RequestElementDTO> headers = RequestTestHelper.generateRandomRequestElements(3);

        final HttpHeaders httpHeaders = requestElementConverter.convertToHttpHeaders(headers);

        checkHttpHeaders(headers, httpHeaders);
    }

    @Test
    public void convertHttpHeadersToRequestElementsTest() {
        final HttpHeaders httpHeaders = RequestTestHelper.generateRandomHttpHeaders(3);

        final List<RequestElementDTO> requestElements = requestElementConverter.convertToRequestElements(httpHeaders);

        checkHttpHeaders(requestElements, httpHeaders);
    }

    private void checkMultiValueMap(final List<RequestElementDTO> parameters,
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

    private void checkHttpHeaders(final List<RequestElementDTO> requestElements,
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
