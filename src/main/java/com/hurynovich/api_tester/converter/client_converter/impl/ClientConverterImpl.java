package com.hurynovich.api_tester.converter.client_converter.impl;

import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestParameterDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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
	public RequestEntity<String> convert(final RequestDTO requestDTO) throws ConverterException {
		try {
			final MultiValueMap<String, String> uriVariables = convertRequestDTOParametersToQueryParams(requestDTO);
			final UriComponentsBuilder uriComponentsBuilder =
				UriComponentsBuilder.fromUri(new URI(requestDTO.getUrl())).queryParams(uriVariables);

			return RequestEntity.method(requestDTO.getMethod(), uriComponentsBuilder.build(uriVariables)).
				headers(requestDTO.getHeaders()).
				body(requestDTO.getBody());
		} catch (final URISyntaxException e) {
			throw new ConverterException("Failed to convert:\n" + requestDTO, e);
		}
	}

	private MultiValueMap<String, String> convertRequestDTOParametersToQueryParams(final RequestDTO requestDTO) {
		final List<RequestParameterDTO> parameters = requestDTO.getParameters();

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
	public ResponseDTO convert(final ResponseEntity<String> responseEntity) {
		final ResponseDTO responseDTO = new ResponseDTO();

		responseDTO.setStatus(responseEntity.getStatusCode());
		responseDTO.setHeaders(responseEntity.getHeaders());
		responseDTO.setBody(responseEntity.getBody());

		return responseDTO;
	}

}
