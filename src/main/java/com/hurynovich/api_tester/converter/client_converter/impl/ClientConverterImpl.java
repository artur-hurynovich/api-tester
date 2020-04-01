package com.hurynovich.api_tester.converter.client_converter.impl;

import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.converter.request_element_converter.RequestNameValueElementConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class ClientConverterImpl implements ClientConverter<String> {

    private final RequestNameValueElementConverter requestNameValueElementConverter;

    public ClientConverterImpl(final @NonNull RequestNameValueElementConverter requestNameValueElementConverter) {
        this.requestNameValueElementConverter = requestNameValueElementConverter;
    }

    @Override
    public RequestEntity<String> convert(final @NonNull RequestDTO request) {
        try {
            final MultiValueMap<String, String> uriVariables =
                    requestNameValueElementConverter.convertToMultiValueMap(request.getParameters());
            final UriComponentsBuilder uriComponentsBuilder =
                    UriComponentsBuilder.fromUri(new URI(request.getUrl())).queryParams(uriVariables);

            final HttpHeaders httpHeaders =
                    requestNameValueElementConverter.convertToHttpHeaders(request.getHeaders());
            return RequestEntity.method(request.getMethod(), uriComponentsBuilder.build(uriVariables)).
                    headers(httpHeaders).
                    body(request.getBody());
        } catch (final URISyntaxException e) {
            throw new ConverterException("Failed to convert request: " + request, e);
        }
    }

    @Override
    public ResponseDTO convert(final @NonNull ResponseEntity<String> responseEntity) {
        final ResponseDTO response = new ResponseDTO();

        response.setStatus(responseEntity.getStatusCode());
        response.setHeaders(requestNameValueElementConverter.convertToRequestElements(responseEntity.getHeaders()));
        response.setBody(responseEntity.getBody());

        return response;
    }

}
