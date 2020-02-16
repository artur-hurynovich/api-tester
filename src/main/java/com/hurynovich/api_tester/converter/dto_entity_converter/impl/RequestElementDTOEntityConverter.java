package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.GeneticDTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestElementEntity;

import org.springframework.stereotype.Service;

@Service
public class RequestElementDTOEntityConverter extends GeneticDTOEntityConverter<RequestElementDTO, RequestElementEntity> {

    @Override
    public Class<RequestElementDTO> getDTOClass() {
        return RequestElementDTO.class;
    }

    @Override
    public Class<RequestElementEntity> getEntityClass() {
        return RequestElementEntity.class;
    }

}
