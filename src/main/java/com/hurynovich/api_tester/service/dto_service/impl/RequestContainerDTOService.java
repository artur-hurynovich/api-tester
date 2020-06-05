package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestContainerDocument;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RequestContainerDTOService extends GenericDTOService<RequestContainerDTO, RequestContainerDocument, String> {

    public RequestContainerDTOService(final @NonNull GenericRepository<RequestContainerDocument, String> repository,
                                      final @NonNull DTOConverter<RequestContainerDTO, RequestContainerDocument, String> converter) {
        super(repository, converter);
    }

    @Override
    protected Class<RequestContainerDocument> getPersistentObjectClass() {
        return RequestContainerDocument.class;
    }
}
