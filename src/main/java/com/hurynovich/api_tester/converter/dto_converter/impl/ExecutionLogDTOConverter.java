package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.GenericDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogEntryDocument;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ExecutionLogDTOConverter extends GenericDTOConverter<ExecutionLogDTO, ExecutionLogDocument, String> {

    private static final String[] IGNORE_PROPERTIES = {"entries"};

    private final DTOConverter<ExecutionLogEntryDTO, ExecutionLogEntryDocument, String> executionLogEntryConverter;

    public ExecutionLogDTOConverter(final @NonNull DTOConverter<ExecutionLogEntryDTO, ExecutionLogEntryDocument, String> executionLogEntryConverter) {
        super(IGNORE_PROPERTIES);

        this.executionLogEntryConverter = executionLogEntryConverter;
    }

    @Override
    public ExecutionLogDocument convert(final ExecutionLogDTO executionLogDTO) {
        final ExecutionLogDocument executionLogDocument = super.convert(executionLogDTO);

        if (executionLogDocument != null) {
            executionLogDocument.setEntries(executionLogEntryConverter.convertAllFromDTO(executionLogDTO.getEntries()));
        }

        return executionLogDocument;
    }

    @Override
    public ExecutionLogDTO convert(final ExecutionLogDocument executionLogDocument) {
        final ExecutionLogDTO executionLogDTO = super.convert(executionLogDocument);

        if (executionLogDTO != null) {
            executionLogDTO.setEntries(executionLogEntryConverter.convertAllToDTO(executionLogDocument.getEntries()));
        }

        return executionLogDTO;
    }

    @Override
    public Class<ExecutionLogDTO> getDTOClass() {
        return ExecutionLogDTO.class;
    }

    @Override
    public Class<ExecutionLogDocument> getPersistentObjectClass() {
        return ExecutionLogDocument.class;
    }

}
