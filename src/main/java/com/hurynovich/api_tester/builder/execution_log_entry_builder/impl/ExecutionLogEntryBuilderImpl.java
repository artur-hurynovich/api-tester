package com.hurynovich.api_tester.builder.execution_log_entry_builder.impl;

import com.hurynovich.api_tester.builder.execution_log_entry_builder.ExecutionLogEntryBuilder;
import com.hurynovich.api_tester.converter.generic_request_element_converter.RequestElementConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ExecutionLogEntryBuilderImpl implements ExecutionLogEntryBuilder {

    private final RequestElementConverter requestElementConverter;

    public ExecutionLogEntryBuilderImpl(final @NonNull RequestElementConverter requestElementConverter) {
        this.requestElementConverter = requestElementConverter;
    }

    @Override
    public ExecutionLogEntryDTO build(final @NonNull RequestDTO request) {
        final ExecutionLogEntryDTO executionLogEntry = new ExecutionLogEntryDTO();

        executionLogEntry.setType(ExecutionLogEntryType.REQUEST);
        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
        executionLogEntry.setMethod(request.getMethod());

        final List<RequestElementDTO> headers = request.getHeaders();
        executionLogEntry.setHeaders(headers);
        executionLogEntry.setUrl(request.getUrl());
        executionLogEntry.setBody(request.getBody());

        return executionLogEntry;
    }

    @Override
    public ExecutionLogEntryDTO build(final @NonNull ResponseDTO response) {
        final ExecutionLogEntryDTO executionLogEntry = new ExecutionLogEntryDTO();

        executionLogEntry.setType(ExecutionLogEntryType.RESPONSE);
        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
        executionLogEntry.setStatus(response.getStatus());
        executionLogEntry.setHeaders(requestElementConverter.
                convertToRequestElements(response.getHeaders()));
        executionLogEntry.setBody(response.getBody());

        return executionLogEntry;
    }

    @Override
    public ExecutionLogEntryDTO build(final @NonNull String errorMessage) {
        final ExecutionLogEntryDTO executionLogEntry = new ExecutionLogEntryDTO();

        executionLogEntry.setType(ExecutionLogEntryType.ERROR);
        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
        executionLogEntry.setErrorMessage(errorMessage);

        return executionLogEntry;
    }

}
