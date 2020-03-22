package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.GenericDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogEntryDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.NameValueElementDocument;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ExecutionLogEntryDTOConverter extends GenericDTOConverter<ExecutionLogEntryDTO, ExecutionLogEntryDocument, String> {

    private static final String[] IGNORE_PROPERTIES = {"headers", "parameters"};

    private final DTOConverter<NameValueElementDTO, NameValueElementDocument, String> nameValueElementDTOConverter;

    public ExecutionLogEntryDTOConverter(final @NonNull DTOConverter<NameValueElementDTO, NameValueElementDocument, String> nameValueElementDTOConverter) {
        super(IGNORE_PROPERTIES);

        this.nameValueElementDTOConverter = nameValueElementDTOConverter;
    }

    @Override
    public ExecutionLogEntryDocument convert(final ExecutionLogEntryDTO executionLogEntryDTO) {
        final ExecutionLogEntryDocument executionLogEntryDocument = super.convert(executionLogEntryDTO);

        if (executionLogEntryDocument != null) {
            executionLogEntryDocument.setHeaders(nameValueElementDTOConverter.convertAllFromDTO(executionLogEntryDTO.getHeaders()));

            executionLogEntryDocument.setParameters(nameValueElementDTOConverter.convertAllFromDTO(executionLogEntryDTO.getParameters()));
        }

        return executionLogEntryDocument;
    }

    @Override
    public ExecutionLogEntryDTO convert(final ExecutionLogEntryDocument executionLogEntryDocument) {
        final ExecutionLogEntryDTO executionLogEntryDTO = super.convert(executionLogEntryDocument);

        if (executionLogEntryDTO != null) {
            executionLogEntryDTO.setHeaders(nameValueElementDTOConverter.convertAllToDTO(executionLogEntryDocument.getHeaders()));

            executionLogEntryDTO.setParameters(nameValueElementDTOConverter.convertAllToDTO(executionLogEntryDocument.getParameters()));
        }

        return executionLogEntryDTO;
    }

    @Override
    public Class<ExecutionLogEntryDTO> getDTOClass() {
        return ExecutionLogEntryDTO.class;
    }

    @Override
    public Class<ExecutionLogEntryDocument> getConvertibleClass() {
        return ExecutionLogEntryDocument.class;
    }

}
