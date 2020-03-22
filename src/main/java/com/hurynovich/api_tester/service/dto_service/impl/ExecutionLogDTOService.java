package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ExecutionLogDTOService extends GenericDTOService<ExecutionLogDTO, ExecutionLogDocument, String> {

    public ExecutionLogDTOService(final @NonNull CrudRepository<ExecutionLogDocument, String> repository,
                                  final @NonNull DTOConverter<ExecutionLogDTO, ExecutionLogDocument, String> converter) {
        super(repository, converter);
    }

}
