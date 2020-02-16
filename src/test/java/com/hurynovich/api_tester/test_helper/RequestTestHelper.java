package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestChainEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestTestHelper {

    private static final int NAME_VALUE_ELEMENT_NAME_MAX_LENGTH = 10;
    private static final int NAME_VALUE_ELEMENT_VALUE_MAX_LENGTH = 10;
    private static final int NAME_VALUE_ELEMENT_EXPRESSION_MAX_LENGTH = 10;
    private static final String HTTP_PROTOCOL = "http";
    private static final int DOMAIN_NAME_LENGTH = 7;
    private static final int DOMAIN_LENGTH = 2;
    private static final int BODY_MIN_LENGTH = 10;
    private static final int BODY_MAX_LENGTH = 100;
    private static final int HEADERS_SIZE = 3;
    private static final int PARAMETERS_SIZE = 3;
    private static final int REQUESTS_IN_CHAIN_SIZE = 5;

    public static HttpMethod generateRandomHttpMethod() {
        return RandomValueGenerator.generateRandomEnumValue(HttpMethod.class);
    }

    public static HttpHeaders generateRandomHttpHeaders(final int size) {
        final HttpHeaders headers = new HttpHeaders();

        IntStream.range(1, size + 1).forEach(index ->
                headers.add(
                        RandomValueGenerator
                                .generateRandomStringLettersOnly(
                                        NAME_VALUE_ELEMENT_NAME_MAX_LENGTH),
                        RandomValueGenerator
                                .generateRandomStringLettersOnly(
                                        NAME_VALUE_ELEMENT_VALUE_MAX_LENGTH)));

        return headers;
    }

    public static List<NameValueElementDTO> generateRandomNameValueElementDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final NameValueElementDTO requestElement = new NameValueElementDTO();

            requestElement.setName(RandomValueGenerator.generateRandomStringLettersOnly(
                    NAME_VALUE_ELEMENT_NAME_MAX_LENGTH));

            requestElement.setValue(RandomValueGenerator.generateRandomStringLettersOnly(
                    NAME_VALUE_ELEMENT_VALUE_MAX_LENGTH));

            requestElement.setExpression(RandomValueGenerator.generateRandomStringLettersOnly(
                    NAME_VALUE_ELEMENT_EXPRESSION_MAX_LENGTH));

            requestElement.setType(RandomValueGenerator.generateRandomEnumValue(NameValueElementType.class));

            return requestElement;
        }).collect(Collectors.toList());
    }

    public static List<NameValueElementEntity> generateRandomNameValueElementEntities(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final NameValueElementEntity requestElement = new NameValueElementEntity();

            requestElement.setName(RandomValueGenerator.generateRandomStringLettersOnly(
                    NAME_VALUE_ELEMENT_NAME_MAX_LENGTH));

            requestElement.setValue(RandomValueGenerator.generateRandomStringLettersOnly(
                    NAME_VALUE_ELEMENT_VALUE_MAX_LENGTH));

            requestElement.setExpression(RandomValueGenerator.generateRandomStringLettersOnly(
                    NAME_VALUE_ELEMENT_EXPRESSION_MAX_LENGTH));

            requestElement.setType(RandomValueGenerator.generateRandomEnumValue(NameValueElementType.class));

            return requestElement;
        }).collect(Collectors.toList());
    }

    public static List<RequestDTO> generateRandomRequestDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final HttpMethod method = generateRandomHttpMethod();
            final List<NameValueElementDTO> headers = generateRandomNameValueElementDTOs(HEADERS_SIZE);
            final String url = generateRandomHttpUrl();
            final List<NameValueElementDTO> parameters = generateRandomNameValueElementDTOs(PARAMETERS_SIZE);
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
            final List<NameValueElementEntity> headers = generateRandomNameValueElementEntities(HEADERS_SIZE);
            final String url = generateRandomHttpUrl();
            final List<NameValueElementEntity> parameters = generateRandomNameValueElementEntities(HEADERS_SIZE);
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

    public static List<RequestChainDTO> generateRandomRequestChainDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final List<RequestDTO> requestDTOs = generateRandomRequestDTOs(REQUESTS_IN_CHAIN_SIZE);

            final RequestChainDTO requestChainDTO = new RequestChainDTO();
            requestChainDTO.setRequests(requestDTOs);

            return requestChainDTO;
        }).collect(Collectors.toList());
    }

    public static List<RequestChainEntity> generateRandomRequestChainEntities(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final List<RequestEntity> requestEntities = generateRandomRequestEntities(REQUESTS_IN_CHAIN_SIZE);

            final RequestChainEntity requestChainEntity = new RequestChainEntity();
            requestChainEntity.setRequests(requestEntities);

            return requestChainEntity;
        }).collect(Collectors.toList());
    }

    public static List<ResponseDTO> generateRandomResponseDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final HttpStatus status = generateRandomHttpStatus();
            final List<NameValueElementDTO> headers = generateRandomNameValueElementDTOs(HEADERS_SIZE);
            final String body = generateRandomBody();

            final ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setStatus(status);
            responseDTO.setHeaders(headers);
            responseDTO.setBody(body);

            return responseDTO;
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
        return RandomValueGenerator.generateRandomStringLettersOnly(BODY_MIN_LENGTH, BODY_MAX_LENGTH);
    }

    public static void checkRequestChainConversion(final RequestChainDTO dto, final RequestChainEntity entity) {
        final List<RequestDTO> dtoRequests = dto.getRequests();
        final List<RequestEntity> entityRequests = entity.getRequests();

        Assertions.assertEquals(dtoRequests.size(), entityRequests.size());
        for (int i = 0; i < dtoRequests.size(); i++) {
            checkRequestConversion(dtoRequests.get(i), entityRequests.get(i));
        }
    }

    public static void checkRequestConversion(final RequestDTO dto, final RequestEntity entity) {
        Assertions.assertEquals(dto.getMethod(), entity.getMethod());

        final List<NameValueElementDTO> dtoHeaders = dto.getHeaders();
        final List<NameValueElementEntity> entityHeaders = entity.getHeaders();
        Assertions.assertEquals(dtoHeaders.size(), entityHeaders.size());
        for (int i = 0; i < dtoHeaders.size(); i++) {
            checkNameValueElementConversion(dtoHeaders.get(i), entityHeaders.get(i));
        }

        Assertions.assertEquals(dto.getUrl(), entity.getUrl());

        final List<NameValueElementDTO> dtoParameters = dto.getParameters();
        final List<NameValueElementEntity> entityParameters = entity.getParameters();
        Assertions.assertEquals(dtoParameters.size(), entityParameters.size());
        for (int i = 0; i < dtoParameters.size(); i++) {
            checkNameValueElementConversion(dtoParameters.get(i), entityParameters.get(i));
        }

        Assertions.assertEquals(dto.getBody(), entity.getBody());
    }

    public static void checkNameValueElementConversion(final NameValueElementDTO dto, final NameValueElementEntity entity) {
        Assertions.assertEquals(dto.getName(), entity.getName());
        Assertions.assertEquals(dto.getValue(), entity.getValue());
        Assertions.assertEquals(dto.getExpression(), entity.getExpression());
        Assertions.assertEquals(dto.getType(), entity.getType());
    }

}
