package com.hurynovich.api_tester.converter.client_converter.impl;

import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestParameterDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public class ClientConverterImpl implements ClientConverter<String> {

    @Override
    public RequestEntity<String> convert(final @NonNull RequestDTO request) throws ConverterException {
        try {
            final MultiValueMap<String, String> uriVariables = convertRequestDTOParametersToQueryParams(request);
            final UriComponentsBuilder uriComponentsBuilder =
                    UriComponentsBuilder.fromUri(new URI(request.getUrl())).queryParams(uriVariables);

            return RequestEntity.method(request.getMethod(), uriComponentsBuilder.build(uriVariables)).
                    headers(request.getHeaders()).
                    body(request.getBody());
        } catch (final URISyntaxException e) {
            throw new ConverterException("Failed to convert request: " + request, e);
        }
    }

    private MultiValueMap<String, String> convertRequestDTOParametersToQueryParams(final @NonNull RequestDTO request) {
        final List<RequestParameterDTO> parameters = request.getParameters();

        if (!CollectionUtils.isEmpty(parameters)) {
            final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

            parameters.forEach(
                    parameter -> queryParams.put(parameter.getName(), Collections.singletonList(parameter.getValue())));

            return queryParams;
        } else {
            return new LinkedMultiValueMap<>();
        }
    }

    @Override
    public ResponseDTO convert(final @NonNull ResponseEntity<String> responseEntity) {
        final ResponseDTO response = new ResponseDTO();

        response.setStatus(responseEntity.getStatusCode());
        response.setHeaders(responseEntity.getHeaders());
        response.setBody(responseEntity.getBody());

        return response;
    }

}
