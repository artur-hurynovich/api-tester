package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.GenericDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestContainerDocument;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestDocument;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RequestContainerDTOConverter extends GenericDTOConverter<RequestContainerDTO, RequestContainerDocument, String> {

    private static final String[] IGNORE_PROPERTIES = {"requests"};

    private final DTOConverter<RequestDTO, RequestDocument, String> requestConverter;

    public RequestContainerDTOConverter(final @NonNull DTOConverter<RequestDTO, RequestDocument, String> requestConverter) {
        super(IGNORE_PROPERTIES);

        this.requestConverter = requestConverter;
    }

    @Override
    public RequestContainerDocument convert(final RequestContainerDTO requestContainerDTO) {
        final RequestContainerDocument requestContainerDocument = super.convert(requestContainerDTO);

        if (requestContainerDocument != null) {
            requestContainerDocument.setRequests(requestConverter.convertAllFromDTO(requestContainerDTO.getRequests()));
        }

        return requestContainerDocument;
    }

    @Override
    public RequestContainerDTO convert(final RequestContainerDocument requestContainerDocument) {
        final RequestContainerDTO requestContainerDTO = super.convert(requestContainerDocument);

        if (requestContainerDTO != null) {
            requestContainerDTO.setRequests(requestConverter.convertAllToDTO(requestContainerDocument.getRequests()));
        }

        return requestContainerDTO;
    }

    @Override
    public Class<RequestContainerDTO> getDTOClass() {
        return RequestContainerDTO.class;
    }

    @Override
    public Class<RequestContainerDocument> getConvertibleClass() {
        return RequestContainerDocument.class;
    }

}
