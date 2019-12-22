package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.dto.impl.RequestParameterDTO;
import com.hurynovich.api_tester.utils.url.constants.UrlUtilsConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestTestHelper {

	private static final Random random = new Random();

	private static final int REQUEST_HEADER_NAME_MAX_LENGTH = 10;
	private static final int REQUEST_HEADER_VALUE_MAX_LENGTH = 10;
	private static final String HTTP_PROTOCOL = "http";
	private static final int DOMAIN_NAME_LENGTH = 7;
	private static final int DOMAIN_LENGTH = 2;
	private static final int REQUEST_PARAMETER_NAME_MAX_LENGTH = 10;
	private static final int REQUEST_PARAMETER_VALUE_MAX_LENGTH = 10;
	private static final int REQUEST_BODY_MIN_LENGTH = 10;
	private static final int REQUEST_BODY_MAX_LENGTH = 100;

	public static HttpMethod generateRandomHttpMethod() {
		return RandomValueGenerator.generateRandomEnumValue(HttpMethod.class);
	}

	public static HttpHeaders generateRandomHttpHeaders(final int size) {
		final HttpHeaders headers = new HttpHeaders();

		IntStream.range(1, size + 1).forEach(index ->
												 headers.add(
													 RandomValueGenerator
														 .generateRandomStringLettersOnly(
															 REQUEST_HEADER_NAME_MAX_LENGTH),
													 RandomValueGenerator
														 .generateRandomStringLettersOnly(
															 REQUEST_HEADER_VALUE_MAX_LENGTH)));

		return headers;
	}

	public static HttpStatus generateRandomHttpStatus() {
		return RandomValueGenerator.generateRandomEnumValue(HttpStatus.class);
	}

	public static String generateRandomUrl() {
		return HTTP_PROTOCOL + "://" + RandomValueGenerator.generateRandomStringLettersOnly(DOMAIN_NAME_LENGTH) + '.' +
			   RandomValueGenerator.generateRandomStringLettersOnly(DOMAIN_LENGTH);
	}


	public static List<RequestParameterDTO> generateRandomParameters(final int size) {
		return IntStream.range(1, size + 1).mapToObj(index -> {
			final RequestParameterDTO requestParameterDTO = new RequestParameterDTO();

			requestParameterDTO.setName(
				RandomValueGenerator
					.generateRandomStringLettersOnly(random.nextInt(REQUEST_PARAMETER_NAME_MAX_LENGTH) + 1)
					.toLowerCase());
			requestParameterDTO.setValue(
				RandomValueGenerator
					.generateRandomStringLettersOnly(random.nextInt(REQUEST_PARAMETER_VALUE_MAX_LENGTH) + 1)
					.toLowerCase());

			return requestParameterDTO;
		}).collect(Collectors.toList());
	}

	public static String generateRandomBody() {
		return RandomValueGenerator.generateRandomStringLettersOnly(REQUEST_BODY_MIN_LENGTH, REQUEST_BODY_MAX_LENGTH);
	}

	public static List<RequestParameterDTO> parseParameters(final String url) {
		final List<RequestParameterDTO> requestParameterDTOS = new ArrayList<>();

		final int parametersPrefixIndex = url.indexOf(UrlUtilsConstants.PARAMETERS_PREFIX);

		if (parametersPrefixIndex != -1 && url.length() > parametersPrefixIndex + 1) {
			final String parametersString = url.substring(parametersPrefixIndex + 1);

			final String[] parameters = parametersString.split(UrlUtilsConstants.PARAMETERS_SEPARATOR);
			if (parameters.length > 0) {
				for (final String parameter : parameters) {
					requestParameterDTOS.add(buildRequestParameterDTO(parameter));
				}
			}
		}

		return requestParameterDTOS;
	}

	private static RequestParameterDTO buildRequestParameterDTO(final String parameter) {
		final RequestParameterDTO requestParameterDTO = new RequestParameterDTO();

		final String[] parameterEntry = parameter.split(UrlUtilsConstants.PARAMETER_NAME_VALUE_SEPARATOR);
		requestParameterDTO.setName(parameterEntry[0]);
		requestParameterDTO.setValue(parameterEntry[1]);

		return requestParameterDTO;
	}

}
