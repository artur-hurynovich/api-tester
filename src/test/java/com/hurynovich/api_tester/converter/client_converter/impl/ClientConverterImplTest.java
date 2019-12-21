package com.hurynovich.api_tester.converter.client_converter.impl;

import com.hurynovich.api_tester.converter.client_converter.ClientConverter;
import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.dto.impl.RequestDTO;
import com.hurynovich.api_tester.dto.impl.RequestParameterDTO;
import com.hurynovich.api_tester.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.helper.RequestTestHelper;
import com.hurynovich.api_tester.utils.url.UrlUtils;
import com.hurynovich.api_tester.utils.url.impl.UrlUtilsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ClientConverterImplTest {

	private static final int REQUEST_HEADERS_SIZE = 3;
	private static final int REQUEST_PARAMETERS_SIZE = 3;

	private static final ClientConverter<String> CLIENT_CONVERTER = new ClientConverterImpl();

	private static final UrlUtils URL_UTILS = new UrlUtilsImpl();

	@Test
	public void convertRequestDTOToRequestEntityTest() throws ConverterException {
		final HttpMethod method = RequestTestHelper.generateRandomHttpMethod();
		final HttpHeaders headers = RequestTestHelper.generateRandomHttpHeaders(REQUEST_HEADERS_SIZE);
		final String url = RequestTestHelper.generateRandomUrl();
		final List<RequestParameterDTO> parameters = RequestTestHelper.generateRandomParameters(REQUEST_PARAMETERS_SIZE);
		final String body = RequestTestHelper.generateRandomBody();

		final RequestDTO requestDTO = new RequestDTO();
		requestDTO.setMethod(method);
		requestDTO.setHeaders(headers);
		requestDTO.setUrl(url);
		requestDTO.setParameters(parameters);
		requestDTO.setBody(body);

		final RequestEntity<String> requestEntity = CLIENT_CONVERTER.convert(requestDTO);
		Assertions.assertEquals(method, requestEntity.getMethod());
		Assertions.assertEquals(headers, requestEntity.getHeaders());
		checkUrl(url, requestEntity);
		checkParameters(parameters, requestEntity);
		Assertions.assertEquals(body, requestEntity.getBody());
	}

	private void checkUrl(final String url, final RequestEntity<String> requestEntity) {
		Assertions.assertEquals(url, URL_UTILS.clearParameters(requestEntity.getUrl().toString()));
	}

	private void checkParameters(final List<RequestParameterDTO> parameters, final RequestEntity<String> requestEntity) {
		final List<RequestParameterDTO> requestParameterDTOS =
			RequestTestHelper.parseParameters(requestEntity.getUrl().toString());

		Assertions.assertEquals(parameters.size(), requestParameterDTOS.size());

		for (int i = 0; i < parameters.size(); i++) {
			final RequestParameterDTO parameter = parameters.get(i);
			final RequestParameterDTO requestParameterDTO = requestParameterDTOS.get(i);

			Assertions.assertEquals(parameter.getName(), requestParameterDTO.getName());
			Assertions.assertEquals(parameter.getValue(), requestParameterDTO.getValue());
		}
	}

	@Test
	public void convertRequestDTOWithIncorrectUrlToRequestEntityTest() {
		final HttpMethod method = RequestTestHelper.generateRandomHttpMethod();
		final HttpHeaders headers = RequestTestHelper.generateRandomHttpHeaders(REQUEST_HEADERS_SIZE);
		final String url = "1 2 3";
		final List<RequestParameterDTO> parameters = RequestTestHelper.generateRandomParameters(REQUEST_PARAMETERS_SIZE);
		final String body = RequestTestHelper.generateRandomBody();

		final RequestDTO requestDTO = new RequestDTO();
		requestDTO.setMethod(method);
		requestDTO.setHeaders(headers);
		requestDTO.setUrl(url);
		requestDTO.setParameters(parameters);
		requestDTO.setBody(body);

		Assertions.assertThrows(ConverterException.class, () -> CLIENT_CONVERTER.convert(requestDTO));
	}

	@Test
	public void convertResponseEntityToResponseDTOTest() {
		final HttpStatus httpStatus = RequestTestHelper.generateRandomHttpStatus();
		final HttpHeaders headers = RequestTestHelper.generateRandomHttpHeaders(REQUEST_HEADERS_SIZE);
		final String body = RequestTestHelper.generateRandomBody();

		final ResponseEntity<String> responseEntity = ResponseEntity.status(httpStatus).headers(headers).body(body);

		final ResponseDTO responseDTO = CLIENT_CONVERTER.convert(responseEntity);
		Assertions.assertEquals(httpStatus, responseDTO.getStatus());
		Assertions.assertEquals(headers, responseDTO.getHeaders());
		Assertions.assertEquals(body, responseDTO.getBody());
	}

}
