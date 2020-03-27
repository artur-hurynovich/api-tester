package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestContainerDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestDocument;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
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
    private static final int EXECUTION_LOG_ENTRIES_SIZE = 5;
    private static final int DOCUMENT_ID_LENGTH = 10;

    private enum Domain {

        COM("com"),
        NET("net"),
        BY("by"),
        RU("ru");

        private final String name;

        Domain(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

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
            final NameValueElementType nameValueElementType =
                    RandomValueGenerator.generateRandomEnumValue(NameValueElementType.class);

            final NameValueElementDTO requestElement = new NameValueElementDTO();

            requestElement.setName(RandomValueGenerator.generateRandomStringLettersOnly(
                    NAME_VALUE_ELEMENT_NAME_MAX_LENGTH));

            switch (nameValueElementType) {
                case VALUE:
                    requestElement.setValue(RandomValueGenerator.generateRandomStringLettersOnly(
                            NAME_VALUE_ELEMENT_VALUE_MAX_LENGTH));
                    break;

                case EXPRESSION:
                    requestElement.setExpression(RandomValueGenerator.generateRandomStringLettersOnly(
                            NAME_VALUE_ELEMENT_EXPRESSION_MAX_LENGTH));
                    break;

                default:
                    throw new RuntimeException("Unknown NameValueElementType + '" + nameValueElementType + "'");
            }

            requestElement.setType(nameValueElementType);

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

    public static List<ResponseDTO> generateRandomResponseDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final HttpStatus status = generateRandomHttpStatus();
            final List<NameValueElementDTO> headers = generateRandomNameValueElementDTOs(HEADERS_SIZE);
            final String body = generateRandomBody();

            final ResponseDTO response = new ResponseDTO();

            response.setStatus(status);
            response.setHeaders(headers);
            response.setBody(body);

            return response;
        }).collect(Collectors.toList());
    }

    public static List<ExecutionLogDocument> generateRandomExecutionLogDocuments(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogDocument executionLog = new ExecutionLogDocument();

            executionLog.setId(RandomValueGenerator.generateRandomStringLettersOnly(DOCUMENT_ID_LENGTH));
            executionLog.setDateTime(LocalDateTime.now());
            executionLog.setUserId(RandomValueGenerator.generateRandomPositiveLong());
            executionLog.setRequestContainerId(RandomValueGenerator.generateRandomStringLettersOnly(DOCUMENT_ID_LENGTH));

            // TODO fix
//            executionLog.setEntries(generateRandomExecutionLogEntries(EXECUTION_LOG_ENTRIES_SIZE));

            return executionLog;
        }).collect(Collectors.toList());
    }

    public static List<ExecutionLogEntryDTO> generateRandomExecutionLogEntries(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogEntryDTO executionLogEntry = new ExecutionLogEntryDTO();

            executionLogEntry.setType(RandomValueGenerator.generateRandomEnumValue(ExecutionLogEntryType.class));
            executionLogEntry.setDateTime(LocalDateTime.now());
            executionLogEntry.setMethod(generateRandomHttpMethod());
            executionLogEntry.setHeaders(generateRandomNameValueElementDTOs(HEADERS_SIZE));
            executionLogEntry.setParameters(generateRandomNameValueElementDTOs(PARAMETERS_SIZE));
            executionLogEntry.setUrl(generateRandomHttpUrl());
            executionLogEntry.setStatus(generateRandomHttpStatus());
            executionLogEntry.setBody(generateRandomBody());
            executionLogEntry.setErrorMessage(generateRandomBody());

            return executionLogEntry;
        }).collect(Collectors.toList());
    }

    public static List<ExecutionLogEntryDTO> generateRandomRequestExecutionLogEntries(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogEntryDTO executionLogEntry = new ExecutionLogEntryDTO();

            executionLogEntry.setType(ExecutionLogEntryType.REQUEST);
            executionLogEntry.setDateTime(LocalDateTime.now());
            executionLogEntry.setMethod(generateRandomHttpMethod());
            executionLogEntry.setHeaders(generateRandomNameValueElementDTOs(HEADERS_SIZE));
            executionLogEntry.setParameters(generateRandomNameValueElementDTOs(PARAMETERS_SIZE));
            executionLogEntry.setUrl(generateRandomHttpUrl());
            executionLogEntry.setBody(generateRandomBody());

            return executionLogEntry;
        }).collect(Collectors.toList());
    }

    public static List<ExecutionLogEntryDTO> generateRandomResponseExecutionLogEntries(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogEntryDTO executionLogEntry = new ExecutionLogEntryDTO();

            executionLogEntry.setType(ExecutionLogEntryType.RESPONSE);
            executionLogEntry.setDateTime(LocalDateTime.now());
            executionLogEntry.setHeaders(generateRandomNameValueElementDTOs(HEADERS_SIZE));
            executionLogEntry.setStatus(generateRandomHttpStatus());
            executionLogEntry.setBody(generateRandomBody());

            return executionLogEntry;
        }).collect(Collectors.toList());
    }

    public static List<ExecutionLogEntryDTO> generateRandomErrorExecutionLogEntries(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogEntryDTO executionLogEntry = new ExecutionLogEntryDTO();

            executionLogEntry.setType(ExecutionLogEntryType.ERROR);
            executionLogEntry.setDateTime(LocalDateTime.now());
            executionLogEntry.setErrorMessage(generateRandomBody());

            return executionLogEntry;
        }).collect(Collectors.toList());
    }

    public static HttpStatus generateRandomHttpStatus() {
        return RandomValueGenerator.generateRandomEnumValue(HttpStatus.class);
    }

    public static String generateRandomHttpUrl() {
        return HTTP_PROTOCOL + "://" + RandomValueGenerator.generateRandomStringLettersOnly(DOMAIN_NAME_LENGTH).toLowerCase() + '.' +
                RandomValueGenerator.generateRandomEnumValue(Domain.class).getName();
    }

    public static String generateRandomBody() {
        return RandomValueGenerator.generateRandomStringLettersOnly(BODY_MIN_LENGTH, BODY_MAX_LENGTH);
    }

    public static void checkRequestContainer(final RequestContainerDocument expected, final RequestContainerDocument actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());

        final List<RequestDocument> expectedRequests = expected.getRequests();
        final List<RequestDocument> actualRequests = actual.getRequests();

        Assertions.assertEquals(expectedRequests.size(), actualRequests.size());

        for (int i = 0; i < expectedRequests.size(); i++) {
            final RequestDocument expectedRequest = expectedRequests.get(i);
            final RequestDocument actualRequest = actualRequests.get(i);
            // TODO implement requests check
//            checkRequest(expectedRequest, actualRequest);
        }
    }

    public static void checkRequest(final RequestDTO expectedRequest, final RequestDTO actualRequest) {
        final List<NameValueElementDTO> expectedHeaders = expectedRequest.getHeaders();
        final List<NameValueElementDTO> actualHeaders = actualRequest.getHeaders();

        Assertions.assertEquals(expectedHeaders.size(), actualHeaders.size());

        for (int i = 0; i < expectedHeaders.size(); i++) {
            final NameValueElementDTO expectedHeader = expectedHeaders.get(i);
            final NameValueElementDTO actualHeader = actualHeaders.get(i);

            checkNameValueElement(expectedHeader, actualHeader);
        }

        Assertions.assertEquals(expectedRequest.getUrl(), actualRequest.getUrl());

        final List<NameValueElementDTO> expectedParameters = expectedRequest.getParameters();
        final List<NameValueElementDTO> actualParameters = actualRequest.getParameters();

        Assertions.assertEquals(expectedParameters.size(), actualParameters.size());

        for (int i = 0; i < expectedHeaders.size(); i++) {
            final NameValueElementDTO expectedParameter = expectedParameters.get(i);
            final NameValueElementDTO actualParameter = actualParameters.get(i);

            checkNameValueElement(expectedParameter, actualParameter);
        }

        Assertions.assertEquals(expectedRequest.getBody(), actualRequest.getBody());
    }

    public static void checkNameValueElement(final NameValueElementDTO expectedNameValueElement,
                                             final NameValueElementDTO actualNameValueElement) {
        Assertions.assertEquals(expectedNameValueElement.getName(), actualNameValueElement.getName());
        Assertions.assertEquals(expectedNameValueElement.getValue(), actualNameValueElement.getValue());
        Assertions.assertEquals(expectedNameValueElement.getExpression(), actualNameValueElement.getExpression());
        Assertions.assertEquals(expectedNameValueElement.getType(), actualNameValueElement.getType());
    }

    public static void checkExecutionLog(final ExecutionLogDocument expectedExecutionLog,
                                         final ExecutionLogDocument actualExecutionLog) {
        Assertions.assertEquals(expectedExecutionLog.getDateTime(), actualExecutionLog.getDateTime());
        Assertions.assertEquals(expectedExecutionLog.getUserId(), actualExecutionLog.getUserId());
        Assertions.assertEquals(expectedExecutionLog.getRequestContainerId(), actualExecutionLog.getRequestContainerId());

        // TODO fix
//        final List<ExecutionLogEntryDTO> expectedExecutionLogEntries = expectedExecutionLog.getEntries();
//        final List<ExecutionLogEntryDTO> actualExecutionLogEntries = actualExecutionLog.getEntries();
//
//        Assertions.assertEquals(expectedExecutionLogEntries.size(), actualExecutionLogEntries.size());
//
//        for (int i = 0; i < expectedExecutionLogEntries.size(); i++) {
//            final ExecutionLogEntryDTO expectedExecutionLogEntry = expectedExecutionLogEntries.get(i);
//            final ExecutionLogEntryDTO actualExecutionLogEntry = actualExecutionLogEntries.get(i);
//
//            checkExecutionLogEntry(expectedExecutionLogEntry, actualExecutionLogEntry);
//        }
    }

    public static void checkExecutionLogEntry(final ExecutionLogEntryDTO expectedExecutionLogEntry,
                                              final ExecutionLogEntryDTO actualExecutionLogEntry) {
        Assertions.assertEquals(expectedExecutionLogEntry.getType(), actualExecutionLogEntry.getType());
        Assertions.assertEquals(expectedExecutionLogEntry.getDateTime(), actualExecutionLogEntry.getDateTime());
        Assertions.assertEquals(expectedExecutionLogEntry.getMethod(), actualExecutionLogEntry.getMethod());

        final List<NameValueElementDTO> expectedExecutionLogEntryHeaders = expectedExecutionLogEntry.getHeaders();
        final List<NameValueElementDTO> actualExecutionLogEntryHeaders = actualExecutionLogEntry.getHeaders();

        Assertions.assertEquals(expectedExecutionLogEntryHeaders.size(), actualExecutionLogEntryHeaders.size());

        for (int i = 0; i < expectedExecutionLogEntryHeaders.size(); i++) {
            final NameValueElementDTO expectedExecutionLogEntryHeader = expectedExecutionLogEntryHeaders.get(i);
            final NameValueElementDTO actualExecutionLogEntryHeader = actualExecutionLogEntryHeaders.get(i);

            checkNameValueElement(expectedExecutionLogEntryHeader, actualExecutionLogEntryHeader);
        }

        final List<NameValueElementDTO> expectedExecutionLogEntryParameters = expectedExecutionLogEntry.getParameters();
        final List<NameValueElementDTO> actualExecutionLogEntryParameters = actualExecutionLogEntry.getParameters();

        Assertions.assertEquals(expectedExecutionLogEntryParameters.size(), actualExecutionLogEntryParameters.size());

        for (int i = 0; i < expectedExecutionLogEntryParameters.size(); i++) {
            final NameValueElementDTO expectedExecutionLogEntryParameter = expectedExecutionLogEntryParameters.get(i);
            final NameValueElementDTO actualExecutionLogEntryParameter = actualExecutionLogEntryParameters.get(i);

            checkNameValueElement(expectedExecutionLogEntryParameter, actualExecutionLogEntryParameter);
        }

        Assertions.assertEquals(expectedExecutionLogEntry.getUrl(), actualExecutionLogEntry.getUrl());
        Assertions.assertEquals(expectedExecutionLogEntry.getStatus(), actualExecutionLogEntry.getStatus());
        Assertions.assertEquals(expectedExecutionLogEntry.getBody(), actualExecutionLogEntry.getBody());
        Assertions.assertEquals(expectedExecutionLogEntry.getErrorMessage(), actualExecutionLogEntry.getErrorMessage());
    }

}
