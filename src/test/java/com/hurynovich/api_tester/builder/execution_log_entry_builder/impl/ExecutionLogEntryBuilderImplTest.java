package com.hurynovich.api_tester.builder.execution_log_entry_builder.impl;

import com.hurynovich.api_tester.builder.execution_log_entry_builder.ExecutionLogEntryBuilder;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType.ERROR;
import static com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType.REQUEST;
import static com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType.RESPONSE;

@ExtendWith(MockitoExtension.class)
public class ExecutionLogEntryBuilderImplTest {

    private ExecutionLogEntryBuilder executionLogEntryBuilder = new ExecutionLogEntryBuilderImpl();

    @Test
    public void buildOfRequestDTOTest() {
        final RequestDTO request = RequestTestHelper.generateRandomRequestDTOs(1).iterator().next();

        final HttpMethod method = request.getMethod();
        final List<NameValueElementDTO> headers = request.getHeaders();
        final String url = request.getUrl();
        final List<NameValueElementDTO> parameters = request.getParameters();
        final String body = request.getBody();

        final ExecutionLogEntryDTO executionLogEntry = executionLogEntryBuilder.build(request);

        Assertions.assertEquals(REQUEST, executionLogEntry.getType());

        final LocalDateTime logEntryDateTime = executionLogEntry.getDateTime();
        final LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());
        Assertions.assertTrue(!logEntryDateTime.isBefore(currentDateTime.minus(500L, ChronoUnit.MILLIS)) &&
                !logEntryDateTime.isAfter(currentDateTime));

        Assertions.assertEquals(method, executionLogEntry.getMethod());

        Assertions.assertEquals(headers, executionLogEntry.getHeaders());
        Assertions.assertEquals(parameters, executionLogEntry.getParameters());
        Assertions.assertEquals(url, executionLogEntry.getUrl());
        Assertions.assertEquals(body, executionLogEntry.getBody());

        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
    }

    @Test
    public void buildOfResponseDTOTest() {
        final ResponseDTO response = RequestTestHelper.generateRandomResponseDTOs(1).iterator().next();

        final HttpStatus status = response.getStatus();
        final List<NameValueElementDTO> headers = response.getHeaders();
        final String body = response.getBody();

        final ExecutionLogEntryDTO executionLogEntry = executionLogEntryBuilder.build(response);

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
        final String errorMessage = RandomValueGenerator.generateRandomStringLettersOnly();

        final ExecutionLogEntryDTO executionLogEntry = executionLogEntryBuilder.build(errorMessage);

        Assertions.assertEquals(ERROR, executionLogEntry.getType());

        final LocalDateTime logEntryDateTime = executionLogEntry.getDateTime();
        final LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());
        Assertions.assertTrue(!logEntryDateTime.isBefore(currentDateTime.minus(500L, ChronoUnit.MILLIS)) &&
                !logEntryDateTime.isAfter(currentDateTime));

        Assertions.assertEquals(errorMessage, executionLogEntry.getErrorMessage());
    }

}
