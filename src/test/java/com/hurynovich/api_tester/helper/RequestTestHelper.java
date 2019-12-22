package com.hurynovich.api_tester.helper;

import com.hurynovich.api_tester.model.dto.impl.RequestParameterDTO;
import com.hurynovich.api_tester.utils.url.constants.UrlUtilsConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
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
		final List<HttpMethod> httpMethods = Arrays.asList(HttpMethod.values());

		final int randomIndex = random.nextInt(httpMethods.size());

		return httpMethods.get(randomIndex);
	}

	public static HttpHeaders generateRandomHttpHeaders(final int size) {
		final HttpHeaders headers = new HttpHeaders();

		IntStream.range(1, size + 1).forEach(index ->
												 headers.add(
													 generateRandomString(REQUEST_HEADER_NAME_MAX_LENGTH),
													 generateRandomString(REQUEST_HEADER_VALUE_MAX_LENGTH)));

		return headers;
	}

	public static HttpStatus generateRandomHttpStatus() {
		final List<HttpStatus> httpStatuses = Arrays.asList(HttpStatus.values());

		final int randomIndex = random.nextInt(httpStatuses.size());

		return httpStatuses.get(randomIndex);
	}

	public static String generateRandomUrl() {
		return HTTP_PROTOCOL + "://" + generateRandomString(DOMAIN_NAME_LENGTH) + '.' +
			   generateRandomString(DOMAIN_LENGTH);
	}

	private static String generateRandomString(final int size) {
		return RandomStringUtils.random(size, true, false);
	}

	public static List<RequestParameterDTO> generateRandomParameters(final int size) {
		return IntStream.range(1, size + 1).mapToObj(index -> {
			final RequestParameterDTO requestParameterDTO = new RequestParameterDTO();

			requestParameterDTO.setName(
				generateRandomString(random.nextInt(REQUEST_PARAMETER_NAME_MAX_LENGTH) + 1).toLowerCase());
			requestParameterDTO.setValue(
				generateRandomString(random.nextInt(REQUEST_PARAMETER_VALUE_MAX_LENGTH) + 1).toLowerCase());

			return requestParameterDTO;
		}).collect(Collectors.toList());
	}

	public static String generateRandomBody() {
		return generateRandomString(REQUEST_BODY_MIN_LENGTH, REQUEST_BODY_MAX_LENGTH);
	}

	public static String generateRandomString(final int minSize, final int maxSize) {
		final int size = minSize + random.nextInt(maxSize);

		return RandomStringUtils.random(size, true, false);
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
