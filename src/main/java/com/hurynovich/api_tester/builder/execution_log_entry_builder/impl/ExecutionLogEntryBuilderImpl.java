package com.hurynovich.api_tester.builder.execution_log_entry_builder.impl;

import com.hurynovich.api_tester.builder.execution_log_entry_builder.ExecutionLogEntryBuilder;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType;
import com.hurynovich.api_tester.model.execution.ExecutionLogEntry;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ExecutionLogEntryBuilderImpl implements ExecutionLogEntryBuilder {

    @Override
    public ExecutionLogEntry build(@NonNull final RequestDTO requestDTO) {
        final ExecutionLogEntry executionLogEntry = new ExecutionLogEntry();

        executionLogEntry.setType(ExecutionLogEntryType.REQUEST);
        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
        executionLogEntry.setMethod(requestDTO.getMethod());
        executionLogEntry.setHeaders(requestDTO.getHeaders());
        executionLogEntry.setUrl(requestDTO.getUrl());
        executionLogEntry.setBody(requestDTO.getBody());

        return executionLogEntry;
    }

    @Override
    public ExecutionLogEntry build(@NonNull final ResponseDTO responseDTO) {
        final ExecutionLogEntry executionLogEntry = new ExecutionLogEntry();

        executionLogEntry.setType(ExecutionLogEntryType.RESPONSE);
        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
        executionLogEntry.setStatus(responseDTO.getStatus());
        executionLogEntry.setHeaders(responseDTO.getHeaders());
        executionLogEntry.setBody(responseDTO.getBody());

        return executionLogEntry;
    }

    @Override
    public ExecutionLogEntry build(final String errorMessage) {
        final ExecutionLogEntry executionLogEntry = new ExecutionLogEntry();

        executionLogEntry.setType(ExecutionLogEntryType.ERROR);
        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
        executionLogEntry.setErrorMessage(errorMessage);

        return executionLogEntry;
    }

}
