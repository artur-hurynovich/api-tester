package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestChainEntity;
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RequestChainDTOService extends GenericDTOService<RequestChainDTO, RequestChainEntity, Long> {

    public RequestChainDTOService(final @NonNull JpaRepository<RequestChainEntity, Long> repository,
                                  final @NonNull DTOEntityConverter<RequestChainDTO, RequestChainEntity> converter) {
        super(repository, converter);
    }

}
