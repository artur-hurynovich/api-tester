package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.GenericDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.NameValueElementDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestDocument;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RequestDTOConverter extends GenericDTOConverter<RequestDTO, RequestDocument, String> {

    private static final String[] IGNORE_PROPERTIES = {"headers", "parameters"};

    private final DTOConverter<NameValueElementDTO, NameValueElementDocument, String> nameValueElementDTOConverter;

    public RequestDTOConverter(final @NonNull DTOConverter<NameValueElementDTO, NameValueElementDocument, String> nameValueElementDTOConverter) {
        super(IGNORE_PROPERTIES);

        this.nameValueElementDTOConverter = nameValueElementDTOConverter;
    }

    @Override
    public RequestDocument convert(final RequestDTO requestDTO) {
        final RequestDocument requestDocument = super.convert(requestDTO);

        if (requestDocument != null) {
            requestDocument.setHeaders(nameValueElementDTOConverter.convertAllFromDTO(requestDTO.getHeaders()));

            requestDocument.setParameters(nameValueElementDTOConverter.convertAllFromDTO(requestDTO.getParameters()));
        }

        return requestDocument;
    }

    @Override
    public RequestDTO convert(final RequestDocument requestDocument) {
        final RequestDTO requestDTO = super.convert(requestDocument);

        if (requestDTO != null) {
            requestDTO.setHeaders(nameValueElementDTOConverter.convertAllToDTO(requestDocument.getHeaders()));

            requestDTO.setParameters(nameValueElementDTOConverter.convertAllToDTO(requestDocument.getParameters()));
        }

        return requestDTO;
    }

    @Override
    public Class<RequestDTO> getDTOClass() {
        return RequestDTO.class;
    }

    @Override
    public Class<RequestDocument> getPersistentObjectClass() {
        return RequestDocument.class;
    }

}
