package com.hurynovich.api_tester.client.impl;

import com.hurynovich.api_tester.client.Client;
import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.converter.client_converter.impl.ClientConverterImpl;
import com.hurynovich.api_tester.converter.request_element_converter.RequestElementConverter;
import com.hurynovich.api_tester.converter.request_element_converter.impl.RequestElementConverterImpl;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {

    private static final String URL = "https://gorest.co.in/public-api/users";

    private static final String APPLICATION_JSON_HEADER_VALUE = "application/json";

    private static final String BODY =
            "\"first_name\":\"Brian\",\"last_name\":\"Ratke\",\"gender\":\"male\",\"email\":\"lew19@roberts.com\",\"status\":\"active\"";

    private final RestTemplate restTemplate = new RestTemplate();

    private final RequestElementConverter requestElementConverter = new RequestElementConverterImpl();

    private final ClientConverter<String> clientConverter = new ClientConverterImpl(requestElementConverter);

    private final Client client = new HttpClient(restTemplate, clientConverter);

    @Test
    public void successGetTest() {
        final RequestDTO request = new RequestDTO();

        request.setMethod(HttpMethod.GET);
        request.setUrl(URL);

        final ResponseDTO response = client.sendRequest(request);

        Assertions.assertNotNull(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void successPostTest() {
        final RequestDTO request = new RequestDTO();

        request.setHeaders(buildHeaders());
        request.setMethod(HttpMethod.POST);
        request.setUrl(URL);
        request.setBody(BODY);

        final ResponseDTO response = client.sendRequest(request);

        Assertions.assertNotNull(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
    }

    private List<NameValueElementDTO> buildHeaders() {
        final List<NameValueElementDTO> headers = new ArrayList<>();

        final NameValueElementDTO acceptHeader = new NameValueElementDTO();
        acceptHeader.setName(HttpHeaders.ACCEPT);
        acceptHeader.setValue(APPLICATION_JSON_HEADER_VALUE);
        headers.add(acceptHeader);

        final NameValueElementDTO contentTypeHeader = new NameValueElementDTO();
        contentTypeHeader.setName(HttpHeaders.CONTENT_TYPE);
        contentTypeHeader.setValue(APPLICATION_JSON_HEADER_VALUE);
        headers.add(contentTypeHeader);

        return headers;
    }

}
