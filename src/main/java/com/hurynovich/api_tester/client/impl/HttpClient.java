package com.hurynovich.api_tester.client.impl;

import com.hurynovich.api_tester.client.Client;
import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpClient implements Client {

    private final RestTemplate restTemplate;

    private final ClientConverter<String> clientConverter;

    public HttpClient(final @NonNull RestTemplate restTemplate,
                      final @NonNull ClientConverter<String> clientConverter) {
        this.restTemplate = restTemplate;
        this.clientConverter = clientConverter;
    }

    @Override
    public ResponseDTO sendRequest(final @NonNull RequestDTO request) {
        final RequestEntity<String> requestEntity = clientConverter.convert(request);

        final ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        return clientConverter.convert(response);
    }

}
