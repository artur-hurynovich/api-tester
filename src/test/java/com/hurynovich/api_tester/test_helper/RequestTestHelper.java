package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.model.enumeration.RequestElementType;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestTestHelper {

    private static final int GENERIC_REQUEST_ELEMENT_NAME_MAX_LENGTH = 10;
    private static final int GENERIC_REQUEST_ELEMENT_VALUE_MAX_LENGTH = 10;
    private static final int GENERIC_REQUEST_ELEMENT_EXPRESSION_MAX_LENGTH = 10;
    private static final String HTTP_PROTOCOL = "http";
    private static final int DOMAIN_NAME_LENGTH = 7;
    private static final int DOMAIN_LENGTH = 2;
    private static final int REQUEST_BODY_MIN_LENGTH = 10;
    private static final int REQUEST_BODY_MAX_LENGTH = 100;
    private static final int REQUEST_HEADER_NAME_MAX_LENGTH = 10;
    private static final int REQUEST_HEADER_VALUE_MAX_LENGTH = 10;
    private static final int REQUEST_HEADERS_SIZE = 3;
    private static final int REQUEST_PARAMETERS_SIZE = 3;

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

    public static List<RequestElementDTO> generateRandomRequestElementDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final RequestElementDTO requestElement = new RequestElementDTO();

            requestElement.setName(RandomValueGenerator.generateRandomStringLettersOnly(
                    GENERIC_REQUEST_ELEMENT_NAME_MAX_LENGTH));

            requestElement.setValue(RandomValueGenerator.generateRandomStringLettersOnly(
                    GENERIC_REQUEST_ELEMENT_VALUE_MAX_LENGTH));

            requestElement.setExpression(RandomValueGenerator.generateRandomStringLettersOnly(
                    GENERIC_REQUEST_ELEMENT_EXPRESSION_MAX_LENGTH));

            requestElement.setType(RandomValueGenerator.generateRandomEnumValue(RequestElementType.class));

            return requestElement;
        }).collect(Collectors.toList());
    }

    public static List<RequestElementEntity> generateRandomRequestElementEntities(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final RequestElementEntity requestElement = new RequestElementEntity();

            requestElement.setName(RandomValueGenerator.generateRandomStringLettersOnly(
                    GENERIC_REQUEST_ELEMENT_NAME_MAX_LENGTH));

            requestElement.setValue(RandomValueGenerator.generateRandomStringLettersOnly(
                    GENERIC_REQUEST_ELEMENT_VALUE_MAX_LENGTH));

            requestElement.setExpression(RandomValueGenerator.generateRandomStringLettersOnly(
                    GENERIC_REQUEST_ELEMENT_EXPRESSION_MAX_LENGTH));

            requestElement.setType(RandomValueGenerator.generateRandomEnumValue(RequestElementType.class));

            return requestElement;
        }).collect(Collectors.toList());
    }

    public static List<RequestDTO> generateRandomRequestDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final HttpMethod method = generateRandomHttpMethod();
            final List<RequestElementDTO> headers = generateRandomRequestElementDTOs(REQUEST_HEADERS_SIZE);
            final String url = generateRandomHttpUrl();
            final List<RequestElementDTO> parameters = generateRandomRequestElementDTOs(REQUEST_PARAMETERS_SIZE);
            final String body = generateRandomBody();

            final RequestDTO request = new RequestDTO();
            request.setMethod(method);
            request.setHeaders(headers);
            request.setUrl(url);
            request.setParameters(parameters);
            request.setBody(body);

            return request;
        }).collect(Collectors.toList());
    }

    public static List<RequestEntity> generateRandomRequestEntities(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final HttpMethod method = generateRandomHttpMethod();
            final List<RequestElementEntity> headers = generateRandomRequestElementEntities(REQUEST_HEADERS_SIZE);
            final String url = generateRandomHttpUrl();
            final List<RequestElementEntity> parameters = generateRandomRequestElementEntities(REQUEST_HEADERS_SIZE);
            final String body = generateRandomBody();

            final RequestEntity requestEntity = new RequestEntity();
            requestEntity.setMethod(method);
            requestEntity.setHeaders(headers);
            requestEntity.setUrl(url);
            requestEntity.setParameters(parameters);
            requestEntity.setBody(body);

            return requestEntity;
        }).collect(Collectors.toList());
    }

    public static HttpStatus generateRandomHttpStatus() {
        return RandomValueGenerator.generateRandomEnumValue(HttpStatus.class);
    }

    public static String generateRandomHttpUrl() {
        return HTTP_PROTOCOL + "://" + RandomValueGenerator.generateRandomStringLettersOnly(DOMAIN_NAME_LENGTH) + '.' +
                RandomValueGenerator.generateRandomStringLettersOnly(DOMAIN_LENGTH);
    }

    public static String generateRandomBody() {
        return RandomValueGenerator.generateRandomStringLettersOnly(REQUEST_BODY_MIN_LENGTH, REQUEST_BODY_MAX_LENGTH);
    }

}
