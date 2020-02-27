package com.hurynovich.api_tester.converter.client_converter.impl;

import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.converter.request_element_converter.RequestElementConverter;
import com.hurynovich.api_tester.converter.request_element_converter.impl.RequestElementConverterImpl;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.utils.RequestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClientConverterImplTest {

    private static final int REQUEST_HEADERS_SIZE = 3;

    private RequestElementConverter requestElementConverter = new RequestElementConverterImpl();

    private ClientConverter<String> clientConverter = new ClientConverterImpl(requestElementConverter);

    @Test
    public void convertRequestDTOToRequestEntityTest() {
        final RequestDTO requestDTO = RequestTestHelper.generateRandomRequestDTOs(1).iterator().next();

        final RequestEntity<String> requestEntity = clientConverter.convert(requestDTO);
        Assertions.assertEquals(requestDTO.getMethod(), requestEntity.getMethod());
        Assertions.assertEquals(requestElementConverter.convertToHttpHeaders(requestDTO.getHeaders()),
                requestEntity.getHeaders());
        checkUrl(requestDTO.getUrl(), requestEntity);

        final List<NameValueElementDTO> requestParameters =
                RequestUtils.parseParameters(requestEntity.getUrl().toString());
        checkNameValueElements(requestDTO.getParameters(), requestParameters);

        Assertions.assertEquals(requestDTO.getBody(), requestEntity.getBody());
    }

    @Test
    public void convertRequestDTOWithIncorrectUrlToRequestEntityTest() {
        final RequestDTO requestDTO = RequestTestHelper.generateRandomRequestDTOs(1).iterator().next();
        requestDTO.setUrl("1 2 3");

        Assertions.assertThrows(ConverterException.class, () -> clientConverter.convert(requestDTO));
    }

    @Test
    public void convertResponseEntityToResponseDTOTest() {
        final HttpStatus httpStatus = RequestTestHelper.generateRandomHttpStatus();
        final List<NameValueElementDTO> headers =
                RequestTestHelper.generateRandomNameValueElementDTOs(REQUEST_HEADERS_SIZE);
        final String body = RequestTestHelper.generateRandomBody();

        final ResponseEntity<String> responseEntity =
                ResponseEntity.
                        status(httpStatus).
                        headers(requestElementConverter.convertToHttpHeaders(headers)).
                        body(body);

        final ResponseDTO response = clientConverter.convert(responseEntity);
        Assertions.assertEquals(httpStatus, response.getStatus());

        checkNameValueElements(headers, response.getHeaders());

        Assertions.assertEquals(body, response.getBody());
    }

    private void checkUrl(final String url, final RequestEntity<String> requestEntity) {
        Assertions.assertEquals(url, RequestUtils.clearParameters(requestEntity.getUrl().toString()));
    }

    private void checkNameValueElements(final List<NameValueElementDTO> expectedElements,
                                        final List<NameValueElementDTO> actualElements) {
        Assertions.assertEquals(expectedElements.size(), actualElements.size());

        for (int i = 0; i < expectedElements.size(); i++) {
            final NameValueElementDTO expectedElement = expectedElements.get(i);
            final NameValueElementDTO actualElement = actualElements.get(i);

            Assertions.assertEquals(expectedElement.getName(), actualElement.getName());
            Assertions.assertEquals(expectedElement.getValue(), actualElement.getValue());
        }
    }

}
