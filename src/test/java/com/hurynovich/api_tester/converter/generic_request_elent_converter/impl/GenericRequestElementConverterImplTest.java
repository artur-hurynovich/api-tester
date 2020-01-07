package com.hurynovich.api_tester.converter.generic_request_elent_converter.impl;

import com.hurynovich.api_tester.converter.generic_request_element_converter.GenericRequestElementConverter;
import com.hurynovich.api_tester.converter.generic_request_element_converter.impl.GenericRequestElementConverterImpl;
import com.hurynovich.api_tester.model.dto.impl.GenericRequestElementDTO;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class GenericRequestElementConverterImplTest {

    private final GenericRequestElementConverter genericRequestElementConverter =
            new GenericRequestElementConverterImpl();

    @Test
    public void convertToMultiValueMapTest() {
        final List<GenericRequestElementDTO> parameters =
                RequestTestHelper.generateRandomGenericRequestElements(3);

        final MultiValueMap<String, String> multiValueMap =
                genericRequestElementConverter.convertToMultiValueMap(parameters);

        checkMultiValueMap(parameters, multiValueMap);
    }

    @Test
    public void convertToHttpHeadersTest() {
        final List<GenericRequestElementDTO> headers =
                RequestTestHelper.generateRandomGenericRequestElements(3);

        final HttpHeaders httpHeaders = genericRequestElementConverter.convertToHttpHeaders(headers);

        checkHttpHeaders(headers, httpHeaders);
    }

    private void checkMultiValueMap(final List<GenericRequestElementDTO> parameters,
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

    private void checkHttpHeaders(final List<GenericRequestElementDTO> parameters,
                                  final HttpHeaders httpHeaders) {
        Assertions.assertEquals(parameters.size(), httpHeaders.size());

        parameters.forEach(parameter -> {
            final String name = parameter.getName();
            final String value = parameter.getValue();

            final List<String> values = httpHeaders.get(name);
            Assertions.assertNotNull(values);
            Assertions.assertEquals(1, values.size());

            Assertions.assertEquals(value, values.get(0));
        });
    }

}
