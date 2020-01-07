package com.hurynovich.api_tester.builder.execution_log_entry_builder.impl;

import com.hurynovich.api_tester.builder.execution_log_entry_builder.ExecutionLogEntryBuilder;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestParameterDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.execution.ExecutionLogEntry;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType.ERROR;
import static com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType.REQUEST;
import static com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType.RESPONSE;

public class ExecutionLogEntryBuilderImplTest {

    private static final int REQUEST_HEADERS_SIZE = 3;
    private static final int REQUEST_PARAMETERS_SIZE = 3;

    private static final ExecutionLogEntryBuilder EXECUTION_LOG_ENTRY_BUILDER = new ExecutionLogEntryBuilderImpl();

    @Test
    public void buildOfRequestDTOTest() {
        final HttpMethod method = RequestTestHelper.generateRandomHttpMethod();
        final HttpHeaders headers = RequestTestHelper.generateRandomHttpHeaders(REQUEST_HEADERS_SIZE);
        final String url = RequestTestHelper.generateRandomHttpUrl();
        final List<RequestParameterDTO> parameters = RequestTestHelper.generateRandomParameters(REQUEST_PARAMETERS_SIZE);
        final String body = RequestTestHelper.generateRandomBody();

        final RequestDTO request = new RequestDTO();
        request.setMethod(method);
        request.setHeaders(headers);
        request.setUrl(url);
        request.setParameters(parameters);
        request.setBody(body);

        final ExecutionLogEntry executionLogEntry = EXECUTION_LOG_ENTRY_BUILDER.build(request);

        Assertions.assertEquals(REQUEST, executionLogEntry.getType());

        final LocalDateTime logEntryDateTime = executionLogEntry.getDateTime();
        final LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());
        Assertions.assertTrue(!logEntryDateTime.isBefore(currentDateTime.minus(500L, ChronoUnit.MILLIS)) &&
                !logEntryDateTime.isAfter(currentDateTime));

        Assertions.assertEquals(method, executionLogEntry.getMethod());
        Assertions.assertEquals(headers, executionLogEntry.getHeaders());
        Assertions.assertEquals(url, executionLogEntry.getUrl());
        Assertions.assertEquals(body, executionLogEntry.getBody());

        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
    }

    @Test
    public void buildOfResponseDTOTest() {
        final HttpStatus status = RequestTestHelper.generateRandomHttpStatus();
        final HttpHeaders headers = RequestTestHelper.generateRandomHttpHeaders(REQUEST_HEADERS_SIZE);
        final String body = RequestTestHelper.generateRandomBody();

        final ResponseDTO response = new ResponseDTO();
        response.setStatus(status);
        response.setHeaders(headers);
        response.setBody(body);

        final ExecutionLogEntry executionLogEntry = EXECUTION_LOG_ENTRY_BUILDER.build(response);

        Assertions.assertEquals(RESPONSE, executionLogEntry.getType());

        final LocalDateTime logEntryDateTime = executionLogEntry.getDateTime();
        final LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());
        Assertions.assertTrue(!logEntryDateTime.isBefore(currentDateTime.minus(500L, ChronoUnit.MILLIS)) &&
                !logEntryDateTime.isAfter(currentDateTime));

        Assertions.assertEquals(status, executionLogEntry.getStatus());
        Assertions.assertEquals(headers, executionLogEntry.getHeaders());
        Assertions.assertEquals(body, executionLogEntry.getBody());
    }

    @Test
    public void buildOfErrorMessageTest() {
        final String errorMessage = RandomValueGenerator.generateRandomStringLettersOnly(10);

        final ExecutionLogEntry executionLogEntry = EXECUTION_LOG_ENTRY_BUILDER.build(errorMessage);

        Assertions.assertEquals(ERROR, executionLogEntry.getType());

        final LocalDateTime logEntryDateTime = executionLogEntry.getDateTime();
        final LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());
        Assertions.assertTrue(!logEntryDateTime.isBefore(currentDateTime.minus(500L, ChronoUnit.MILLIS)) &&
                !logEntryDateTime.isAfter(currentDateTime));

        Assertions.assertEquals(errorMessage, executionLogEntry.getErrorMessage());
    }

}
