package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogEntryDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.NameValueElementDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestContainerDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestDocument;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestTestHelper {

    private static final String HTTP_PROTOCOL = "http";
    private static final int HEADERS_SIZE = 3;
    private static final int PARAMETERS_SIZE = 3;
    private static final int EXECUTION_LOG_ENTRIES_SIZE = 5;
    private static final int REQUESTS_SIZE = 5;

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
                headers.add(RandomValueGenerator.generateRandomStringLettersOnly(),
                        RandomValueGenerator.generateRandomStringLettersOnly()));

        return headers;
    }

    public static List<NameValueElementDTO> generateRandomNameValueElementDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final NameValueElementDTO nameValueElementDTO = new NameValueElementDTO();

            nameValueElementDTO.setId(RandomValueGenerator.generateRandomStringLettersOnly());

            nameValueElementDTO.setName(RandomValueGenerator.generateRandomStringLettersOnly());

            final NameValueElementType nameValueElementType =
                    RandomValueGenerator.generateRandomEnumValue(NameValueElementType.class);
            nameValueElementDTO.setType(nameValueElementType);

            switch (nameValueElementType) {
                case VALUE:
                    nameValueElementDTO.setValue(RandomValueGenerator.generateRandomStringLettersOnly());
                    break;

                case EXPRESSION:
                    nameValueElementDTO.setExpression(RandomValueGenerator.generateRandomStringLettersOnly());
                    break;

                default:
                    throw new RuntimeException("Unknown NameValueElementType + '" + nameValueElementType + "'");
            }

            return nameValueElementDTO;
        }).collect(Collectors.toList());
    }

    public static List<NameValueElementDocument> generateRandomNameValueElementDocuments(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final NameValueElementDocument nameValueElementDocument = new NameValueElementDocument();

            nameValueElementDocument.setId(RandomValueGenerator.generateRandomStringLettersOnly());

            nameValueElementDocument.setName(RandomValueGenerator.generateRandomStringLettersOnly());

            final NameValueElementType nameValueElementType =
                    RandomValueGenerator.generateRandomEnumValue(NameValueElementType.class);
            nameValueElementDocument.setType(nameValueElementType);

            switch (nameValueElementType) {
                case VALUE:
                    nameValueElementDocument.setValue(RandomValueGenerator.generateRandomStringLettersOnly());
                    break;

                case EXPRESSION:
                    nameValueElementDocument.setExpression(RandomValueGenerator.generateRandomStringLettersOnly());
                    break;

                default:
                    throw new RuntimeException("Unknown NameValueElementType + '" + nameValueElementType + "'");
            }

            return nameValueElementDocument;
        }).collect(Collectors.toList());
    }

    public static void checkNameValueElementConversion(final NameValueElementDTO nameValueElementDTO,
                                                       final NameValueElementDocument nameValueElementDocument) {
        Assertions.assertTrue((nameValueElementDTO == null && nameValueElementDocument == null) ||
                (nameValueElementDTO != null && nameValueElementDocument != null));

        if (nameValueElementDTO != null) {
            Assertions.assertEquals(nameValueElementDTO.getId(), nameValueElementDocument.getId());
            Assertions.assertEquals(nameValueElementDTO.getName(), nameValueElementDocument.getName());
            Assertions.assertEquals(nameValueElementDTO.getType(), nameValueElementDocument.getType());

            final String nameValueElementDTOValue = nameValueElementDTO.getValue();
            final String nameValueElementDocumentValue = nameValueElementDocument.getValue();
            Assertions.assertTrue((nameValueElementDTOValue == null && nameValueElementDocumentValue == null) ||
                    (nameValueElementDTOValue != null && nameValueElementDTOValue.equals(nameValueElementDocumentValue)));

            final String nameValueElementDTOExpression = nameValueElementDTO.getExpression();
            final String nameValueElementDocumentExpression = nameValueElementDocument.getExpression();
            Assertions.assertTrue((nameValueElementDTOExpression == null && nameValueElementDocumentExpression == null) ||
                    (nameValueElementDTOExpression != null && nameValueElementDTOExpression.equals(nameValueElementDocumentExpression)));
        }
    }

    public static List<RequestDTO> generateRandomRequestDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final RequestDTO requestDTO = new RequestDTO();

            requestDTO.setId(RandomValueGenerator.generateRandomStringLettersOnly());
            requestDTO.setMethod(generateRandomHttpMethod());
            requestDTO.setHeaders(generateRandomNameValueElementDTOs(HEADERS_SIZE));
            requestDTO.setUrl(generateRandomHttpUrl());
            requestDTO.setParameters(generateRandomNameValueElementDTOs(PARAMETERS_SIZE));
            requestDTO.setBody(generateRandomBody());

            return requestDTO;
        }).collect(Collectors.toList());
    }

    public static List<RequestDocument> generateRandomRequestDocuments(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final RequestDocument requestDocument = new RequestDocument();

            requestDocument.setId(RandomValueGenerator.generateRandomStringLettersOnly());
            requestDocument.setMethod(generateRandomHttpMethod());
            requestDocument.setHeaders(generateRandomNameValueElementDocuments(HEADERS_SIZE));
            requestDocument.setUrl(generateRandomHttpUrl());
            requestDocument.setParameters(generateRandomNameValueElementDocuments(PARAMETERS_SIZE));
            requestDocument.setBody(generateRandomBody());

            return requestDocument;
        }).collect(Collectors.toList());
    }

    public static void checkRequestConversion(final RequestDTO requestDTO,
                                              final RequestDocument requestDocument) {
        Assertions.assertTrue((requestDTO == null && requestDocument == null) ||
                (requestDTO != null && requestDocument != null));

        if (requestDTO != null) {
            Assertions.assertEquals(requestDTO.getId(), requestDocument.getId());
            Assertions.assertEquals(requestDTO.getMethod(), requestDocument.getMethod());

            final List<NameValueElementDTO> requestDTOHeaders = requestDTO.getHeaders();
            final List<NameValueElementDocument> requestDocumentHeaders = requestDocument.getHeaders();
            Assertions.assertEquals(requestDTOHeaders.size(), requestDocumentHeaders.size());
            for (int i = 0; i < requestDTOHeaders.size(); i++) {
                checkNameValueElementConversion(requestDTOHeaders.get(i), requestDocumentHeaders.get(i));
            }

            Assertions.assertEquals(requestDTO.getUrl(), requestDocument.getUrl());

            final List<NameValueElementDTO> requestDTOParameters = requestDTO.getParameters();
            final List<NameValueElementDocument> requestDocumentParameters = requestDocument.getParameters();
            Assertions.assertEquals(requestDTOParameters.size(), requestDocumentParameters.size());
            for (int i = 0; i < requestDTOParameters.size(); i++) {
                checkNameValueElementConversion(requestDTOParameters.get(i), requestDocumentParameters.get(i));
            }

            Assertions.assertEquals(requestDTO.getBody(), requestDocument.getBody());
        }
    }

    public static List<RequestContainerDTO> generateRandomRequestContainerDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final RequestContainerDTO requestContainerDTO = new RequestContainerDTO();

            requestContainerDTO.setId(RandomValueGenerator.generateRandomStringLettersOnly());
            requestContainerDTO.setName(RandomValueGenerator.generateRandomStringLettersOnly());
            requestContainerDTO.setDescription(RandomValueGenerator.generateRandomStringLettersOnly());
            requestContainerDTO.setRequests(generateRandomRequestDTOs(REQUESTS_SIZE));

            return requestContainerDTO;
        }).collect(Collectors.toList());
    }

    public static List<RequestContainerDocument> generateRandomRequestContainerDocuments(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final RequestContainerDocument requestContainerDocument = new RequestContainerDocument();

            requestContainerDocument.setId(RandomValueGenerator.generateRandomStringLettersOnly());
            requestContainerDocument.setName(RandomValueGenerator.generateRandomStringLettersOnly());
            requestContainerDocument.setDescription(RandomValueGenerator.generateRandomStringLettersOnly());
            requestContainerDocument.setRequests(generateRandomRequestDocuments(REQUESTS_SIZE));

            return requestContainerDocument;
        }).collect(Collectors.toList());
    }

    public static void checkRequestContainerConversion(final RequestContainerDTO requestContainerDTO,
                                                       final RequestContainerDocument requestContainerDocument) {
        Assertions.assertTrue((requestContainerDTO == null && requestContainerDocument == null) ||
                (requestContainerDTO != null && requestContainerDocument != null));

        if (requestContainerDTO != null) {
            Assertions.assertEquals(requestContainerDTO.getId(), requestContainerDocument.getId());
            Assertions.assertEquals(requestContainerDTO.getName(), requestContainerDocument.getName());
            Assertions.assertEquals(requestContainerDTO.getDescription(), requestContainerDocument.getDescription());

            final List<RequestDTO> requestContainerDTORequests = requestContainerDTO.getRequests();
            final List<RequestDocument> requestContainerDocumentRequests = requestContainerDocument.getRequests();
            Assertions.assertEquals(requestContainerDTORequests.size(), requestContainerDocumentRequests.size());
            for (int i = 0; i < requestContainerDTORequests.size(); i++) {
                checkRequestConversion(requestContainerDTORequests.get(i), requestContainerDocumentRequests.get(i));
            }
        }
    }

    public static void checkRequestContainer(final RequestContainerDTO expected, final RequestContainerDTO actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());

        final List<RequestDTO> expectedRequests = expected.getRequests();
        final List<RequestDTO> actualRequests = actual.getRequests();

        Assertions.assertEquals(expectedRequests.size(), actualRequests.size());

        for (int i = 0; i < expectedRequests.size(); i++) {
            checkRequest(expectedRequests.get(i), actualRequests.get(i));
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

    public static List<ResponseDTO> generateRandomResponseDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ResponseDTO response = new ResponseDTO();

            response.setStatus(generateRandomHttpStatus());
            response.setHeaders(generateRandomNameValueElementDTOs(HEADERS_SIZE));
            response.setBody(generateRandomBody());

            return response;
        }).collect(Collectors.toList());
    }

    public static List<UserDTO> generateRandomUserDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final UserDTO user = new UserDTO();

            user.setId(RandomValueGenerator.generateRandomPositiveLong());
            user.setName(RandomValueGenerator.generateRandomStringLettersOnly());

            return user;
        }).collect(Collectors.toList());
    }

    public static List<UserEntity> generateRandomUserEntities(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final UserEntity user = new UserEntity();

            user.setId(RandomValueGenerator.generateRandomPositiveLong());
            user.setName(RandomValueGenerator.generateRandomStringLettersOnly());

            return user;
        }).collect(Collectors.toList());
    }

    public static void checkUserConversion(final UserDTO userDTO, final UserEntity userEntity) {
        Assertions.assertTrue((userDTO == null && userEntity == null) ||
                (userDTO != null && userEntity != null));

        if (userDTO != null) {
            Assertions.assertEquals(userDTO.getId(), userEntity.getId());
            Assertions.assertEquals(userDTO.getName(), userEntity.getName());
        }
    }

    public static void checkUserDTOs(final UserDTO expectedUser, final UserDTO actualUser) {
        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
    }

    public static List<ExecutionLogDTO> generateRandomExecutionLogDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogDTO executionLogDTO = new ExecutionLogDTO();

            executionLogDTO.setId(RandomValueGenerator.generateRandomStringLettersOnly());
            executionLogDTO.setDateTime(LocalDateTime.now());
            executionLogDTO.setUserId(RandomValueGenerator.generateRandomPositiveLong());
            executionLogDTO.setEntries(generateRandomExecutionLogEntryDTOs(EXECUTION_LOG_ENTRIES_SIZE));

            return executionLogDTO;
        }).collect(Collectors.toList());
    }

    public static List<ExecutionLogDocument> generateRandomExecutionLogDocuments(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogDocument executionLogDocument = new ExecutionLogDocument();

            executionLogDocument.setId(RandomValueGenerator.generateRandomStringLettersOnly());
            executionLogDocument.setDateTime(LocalDateTime.now());
            executionLogDocument.setUserId(RandomValueGenerator.generateRandomPositiveLong());
            executionLogDocument.setEntries(generateRandomExecutionLogEntryDocuments(EXECUTION_LOG_ENTRIES_SIZE));

            return executionLogDocument;
        }).collect(Collectors.toList());
    }

    public static void checkExecutionLogConversion(final ExecutionLogDTO executionLogDTO,
                                                   final ExecutionLogDocument executionLogDocument) {
        Assertions.assertTrue((executionLogDTO == null && executionLogDocument == null) ||
                (executionLogDTO != null && executionLogDocument != null));

        if (executionLogDTO != null) {
            Assertions.assertEquals(executionLogDTO.getId(), executionLogDocument.getId());
            Assertions.assertEquals(executionLogDTO.getDateTime(), executionLogDocument.getDateTime());
            Assertions.assertEquals(executionLogDTO.getUserId(), executionLogDocument.getUserId());

            final List<ExecutionLogEntryDTO> executionLogDTOEntries = executionLogDTO.getEntries();
            final List<ExecutionLogEntryDocument> executionLogDocumentEntries = executionLogDocument.getEntries();
            Assertions.assertEquals(executionLogDTOEntries.size(), executionLogDocumentEntries.size());
            for (int i = 0; i < executionLogDTOEntries.size(); i++) {
                checkExecutionLogEntryConversion(executionLogDTOEntries.get(i), executionLogDocumentEntries.get(i));
            }
        }
    }

    public static void checkExecutionLog(final ExecutionLogDTO expectedExecutionLog,
                                         final ExecutionLogDTO actualExecutionLog) {
        Assertions.assertEquals(expectedExecutionLog.getDateTime(), actualExecutionLog.getDateTime());
        Assertions.assertEquals(expectedExecutionLog.getUserId(), actualExecutionLog.getUserId());

        final List<ExecutionLogEntryDTO> expectedExecutionLogEntries = expectedExecutionLog.getEntries();
        final List<ExecutionLogEntryDTO> actualExecutionLogEntries = actualExecutionLog.getEntries();

        Assertions.assertEquals(expectedExecutionLogEntries.size(), actualExecutionLogEntries.size());

        for (int i = 0; i < expectedExecutionLogEntries.size(); i++) {
            final ExecutionLogEntryDTO expectedExecutionLogEntry = expectedExecutionLogEntries.get(i);
            final ExecutionLogEntryDTO actualExecutionLogEntry = actualExecutionLogEntries.get(i);

            checkExecutionLogEntry(expectedExecutionLogEntry, actualExecutionLogEntry);
        }
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

    public static List<ExecutionLogEntryDTO> generateRandomExecutionLogEntryDTOs(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogEntryDTO executionLogEntryDTO = new ExecutionLogEntryDTO();

            executionLogEntryDTO.setType(RandomValueGenerator.generateRandomEnumValue(ExecutionLogEntryType.class));
            executionLogEntryDTO.setDateTime(LocalDateTime.now());
            executionLogEntryDTO.setMethod(generateRandomHttpMethod());
            executionLogEntryDTO.setHeaders(generateRandomNameValueElementDTOs(HEADERS_SIZE));
            executionLogEntryDTO.setParameters(generateRandomNameValueElementDTOs(PARAMETERS_SIZE));
            executionLogEntryDTO.setUrl(generateRandomHttpUrl());
            executionLogEntryDTO.setStatus(generateRandomHttpStatus());
            executionLogEntryDTO.setBody(generateRandomBody());
            executionLogEntryDTO.setErrorMessage(generateRandomBody());

            return executionLogEntryDTO;
        }).collect(Collectors.toList());
    }

    public static List<ExecutionLogEntryDocument> generateRandomExecutionLogEntryDocuments(final int size) {
        return IntStream.range(1, size + 1).mapToObj(index -> {
            final ExecutionLogEntryDocument executionLogEntryDocument = new ExecutionLogEntryDocument();

            executionLogEntryDocument.setType(RandomValueGenerator.generateRandomEnumValue(ExecutionLogEntryType.class));
            executionLogEntryDocument.setDateTime(LocalDateTime.now());
            executionLogEntryDocument.setMethod(generateRandomHttpMethod());
            executionLogEntryDocument.setHeaders(generateRandomNameValueElementDocuments(HEADERS_SIZE));
            executionLogEntryDocument.setParameters(generateRandomNameValueElementDocuments(PARAMETERS_SIZE));
            executionLogEntryDocument.setUrl(generateRandomHttpUrl());
            executionLogEntryDocument.setStatus(generateRandomHttpStatus());
            executionLogEntryDocument.setBody(generateRandomBody());
            executionLogEntryDocument.setErrorMessage(generateRandomBody());

            return executionLogEntryDocument;
        }).collect(Collectors.toList());
    }

    public static void checkExecutionLogEntryConversion(final ExecutionLogEntryDTO executionLogEntryDTO,
                                                        final ExecutionLogEntryDocument executionLogEntryDocument) {
        Assertions.assertTrue((executionLogEntryDTO == null && executionLogEntryDocument == null) ||
                (executionLogEntryDTO != null && executionLogEntryDocument != null));

        if (executionLogEntryDTO != null) {
            Assertions.assertEquals(executionLogEntryDTO.getId(), executionLogEntryDocument.getId());
            Assertions.assertEquals(executionLogEntryDTO.getType(), executionLogEntryDocument.getType());
            Assertions.assertEquals(executionLogEntryDTO.getDateTime(), executionLogEntryDocument.getDateTime());
            Assertions.assertEquals(executionLogEntryDTO.getMethod(), executionLogEntryDocument.getMethod());

            final List<NameValueElementDTO> executionLogEntryDTOHeaders = executionLogEntryDTO.getHeaders();
            final List<NameValueElementDocument> executionLogEntryDocumentHeaders = executionLogEntryDocument.getHeaders();
            Assertions.assertEquals(executionLogEntryDTOHeaders.size(), executionLogEntryDocumentHeaders.size());
            for (int i = 0; i < executionLogEntryDTOHeaders.size(); i++) {
                checkNameValueElementConversion(executionLogEntryDTOHeaders.get(i), executionLogEntryDocumentHeaders.get(i));
            }

            final List<NameValueElementDTO> executionLogEntryDTOParameters = executionLogEntryDTO.getParameters();
            final List<NameValueElementDocument> executionLogEntryDocumentParameters = executionLogEntryDocument.getParameters();
            Assertions.assertEquals(executionLogEntryDTOParameters.size(), executionLogEntryDocumentParameters.size());
            for (int i = 0; i < executionLogEntryDTOParameters.size(); i++) {
                checkNameValueElementConversion(executionLogEntryDTOParameters.get(i), executionLogEntryDocumentParameters.get(i));
            }

            Assertions.assertEquals(executionLogEntryDTO.getUrl(), executionLogEntryDocument.getUrl());
            Assertions.assertEquals(executionLogEntryDTO.getStatus(), executionLogEntryDocument.getStatus());
            Assertions.assertEquals(executionLogEntryDTO.getBody(), executionLogEntryDocument.getBody());
            Assertions.assertEquals(executionLogEntryDTO.getErrorMessage(), executionLogEntryDocument.getErrorMessage());
        }
    }

    public static List<ExecutionLogEntryDTO> generateRandomRequestExecutionLogEntryDTOs(final int size) {
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

    public static List<ExecutionLogEntryDTO> generateRandomResponseExecutionLogEntryDTOs(final int size) {
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

    public static List<ExecutionLogEntryDTO> generateRandomErrorExecutionLogEntryDTOs(final int size) {
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
        return HTTP_PROTOCOL + "://" + RandomValueGenerator.generateRandomStringLettersOnly().toLowerCase() + '.' +
                RandomValueGenerator.generateRandomEnumValue(Domain.class).getName();
    }

    public static String generateRandomBody() {
        return RandomValueGenerator.generateRandomStringLettersOnly();
    }

}
