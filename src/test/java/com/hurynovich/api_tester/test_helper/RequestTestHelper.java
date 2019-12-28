package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.dto.impl.RequestParameterDTO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestTestHelper {

    private static final Random RANDOM = new Random();

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

    public static String generateRandomHttpUrl() {
        return HTTP_PROTOCOL + "://" + RandomValueGenerator.generateRandomStringLettersOnly(DOMAIN_NAME_LENGTH) + '.' +
                RandomValueGenerator.generateRandomStringLettersOnly(DOMAIN_LENGTH);
    }


    public static List<RequestParameterDTO> generateRandomParameters(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final RequestParameterDTO requestParameter = new RequestParameterDTO();

            requestParameter.setName(
                    RandomValueGenerator
                            .generateRandomStringLettersOnly(RANDOM.nextInt(REQUEST_PARAMETER_NAME_MAX_LENGTH) + 1)
                            .toLowerCase());
            requestParameter.setValue(
                    RandomValueGenerator
                            .generateRandomStringLettersOnly(RANDOM.nextInt(REQUEST_PARAMETER_VALUE_MAX_LENGTH) + 1)
                            .toLowerCase());

            return requestParameter;
        }).collect(Collectors.toList());
    }

    public static String generateRandomBody() {
        return RandomValueGenerator.generateRandomStringLettersOnly(REQUEST_BODY_MIN_LENGTH, REQUEST_BODY_MAX_LENGTH);
    }

}
