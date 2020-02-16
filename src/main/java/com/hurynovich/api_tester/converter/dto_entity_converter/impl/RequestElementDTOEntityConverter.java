package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.GeneticDTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;

import org.springframework.stereotype.Service;

@Service
public class RequestElementDTOEntityConverter extends GeneticDTOEntityConverter<NameValueElementDTO, NameValueElementEntity> {

    @Override
    public Class<NameValueElementDTO> getDTOClass() {
        return NameValueElementDTO.class;
    }

    @Override
    public Class<NameValueElementEntity> getEntityClass() {
        return NameValueElementEntity.class;
    }

}
