package com.hurynovich.api_tester.converter.client_converter.impl;

import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.converter.generic_request_element_converter.RequestElementConverter;
import com.hurynovich.api_tester.converter.generic_request_element_converter.impl.RequestElementConverterImpl;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.utils.RequestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClientConverterImplTest {

    private static final int REQUEST_HEADERS_SIZE = 3;
    private static final int REQUEST_PARAMETERS_SIZE = 3;

    private RequestElementConverter requestElementConverter = new RequestElementConverterImpl();

    private ClientConverter<String> clientConverter;

    @BeforeEach
    public void init() {
        clientConverter = new ClientConverterImpl(requestElementConverter);
    }

    @Test
    public void convertRequestDTOToRequestEntityTest() throws ConverterException {
        final HttpMethod method = RequestTestHelper.generateRandomHttpMethod();
        final List<RequestElementDTO> headers =
                RequestTestHelper.generateRandomRequestElementDTOs(REQUEST_HEADERS_SIZE);
        final String url = RequestTestHelper.generateRandomHttpUrl();
        final List<RequestElementDTO> parameters =
                RequestTestHelper.generateRandomRequestElementDTOs(REQUEST_PARAMETERS_SIZE);
        final String body = RequestTestHelper.generateRandomBody();

        final RequestDTO request = new RequestDTO();
        request.setMethod(method);
        request.setHeaders(headers);
        request.setUrl(url);
        request.setParameters(parameters);
        request.setBody(body);

        final RequestEntity<String> requestEntity = clientConverter.convert(request);
        Assertions.assertEquals(method, requestEntity.getMethod());
        Assertions.assertEquals(requestElementConverter.convertToHttpHeaders(headers),
                requestEntity.getHeaders());
        checkUrl(url, requestEntity);
        checkParameters(parameters, requestEntity);
        Assertions.assertEquals(body, requestEntity.getBody());
    }

    private void checkUrl(final String url, final RequestEntity<String> requestEntity) {
        Assertions.assertEquals(url, RequestUtils.clearParameters(requestEntity.getUrl().toString()));
    }

    private void checkParameters(final List<RequestElementDTO> parameters,
                                 final RequestEntity<String> requestEntity) {
        final List<RequestElementDTO> requestParameters =
                RequestUtils.parseParameters(requestEntity.getUrl().toString());

        Assertions.assertEquals(parameters.size(), requestParameters.size());

        for (int i = 0; i < parameters.size(); i++) {
            final RequestElementDTO parameter = parameters.get(i);
            final RequestElementDTO requestParameter = requestParameters.get(i);

            Assertions.assertEquals(parameter.getName(), requestParameter.getName());
            Assertions.assertEquals(parameter.getValue(), requestParameter.getValue());
        }
    }

    @Test
    public void convertRequestDTOWithIncorrectUrlToRequestEntityTest() {
        final HttpMethod method = RequestTestHelper.generateRandomHttpMethod();
        final List<RequestElementDTO> headers =
                RequestTestHelper.generateRandomRequestElementDTOs(REQUEST_HEADERS_SIZE);
        final String nonValidUrl = "1 2 3";
        final List<RequestElementDTO> parameters =
                RequestTestHelper.generateRandomRequestElementDTOs(REQUEST_PARAMETERS_SIZE);
        final String body = RequestTestHelper.generateRandomBody();

        final RequestDTO request = new RequestDTO();
        request.setMethod(method);
        request.setHeaders(headers);
        request.setUrl(nonValidUrl);
        request.setParameters(parameters);
        request.setBody(body);

        Assertions.assertThrows(ConverterException.class, () -> clientConverter.convert(request));
    }

    @Test
    public void convertResponseEntityToResponseDTOTest() {
        final HttpStatus httpStatus = RequestTestHelper.generateRandomHttpStatus();
        final List<RequestElementDTO> headers =
                RequestTestHelper.generateRandomRequestElementDTOs(REQUEST_HEADERS_SIZE);
        final String body = RequestTestHelper.generateRandomBody();

        final ResponseEntity<String> responseEntity =
                ResponseEntity.
                        status(httpStatus).
                        headers(requestElementConverter.convertToHttpHeaders(headers)).
                        body(body);

        final ResponseDTO response = clientConverter.convert(responseEntity);
        Assertions.assertEquals(httpStatus, response.getStatus());
        Assertions.assertEquals(requestElementConverter.convertToHttpHeaders(headers),
                response.getHeaders());
        Assertions.assertEquals(body, response.getBody());
    }

}
