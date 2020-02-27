package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RequestDTOService extends GenericDTOService<RequestDTO, RequestEntity, Long> {

    public RequestDTOService(final @NonNull JpaRepository<RequestEntity, Long> repository,
                             final @NonNull DTOEntityConverter<RequestDTO, RequestEntity> converter) {
        super(repository, converter);
    }

}
