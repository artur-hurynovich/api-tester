package com.hurynovich.api_tester.client.impl;

import com.hurynovich.api_tester.client.Client;
import com.hurynovich.api_tester.client.exception.ClientException;
import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpClient implements Client {

	private RestTemplate restTemplate;

	private ClientConverter<String> clientConverter;

	public HttpClient(final RestTemplate restTemplate,
					  final ClientConverter<String> clientConverter) {
		this.restTemplate = restTemplate;
		this.clientConverter = clientConverter;
	}

	@Override
	public ResponseDTO sendRequest(final RequestDTO request) throws ClientException {
		try {
			final RequestEntity<String> requestEntity = clientConverter.convert(request);

			final ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

			return clientConverter.convert(response);
		} catch (final ConverterException e) {
			throw new ClientException("Failed to send request:\n" + request, e);
		}
	}

}
