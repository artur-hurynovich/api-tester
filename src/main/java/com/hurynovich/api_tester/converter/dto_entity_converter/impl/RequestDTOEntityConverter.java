package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.converter.dto_entity_converter.GeneticDTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;

import org.springframework.stereotype.Service;

@Service
public class RequestDTOEntityConverter extends GeneticDTOEntityConverter<RequestDTO, RequestEntity> {

    private static final String[] IGNORE_PROPERTIES = {"headers", "parameters"};

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> requestElementConverter;

    public RequestDTOEntityConverter(final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> requestElementConverter) {
        super(IGNORE_PROPERTIES);

        this.requestElementConverter = requestElementConverter;
    }

    @Override
    public RequestEntity convert(final RequestDTO requestDTO) {
        final RequestEntity requestEntity = super.convert(requestDTO);

        if (requestEntity != null) {
            requestEntity.setHeaders(requestElementConverter.convertAllToEntity(requestDTO.getHeaders()));
            requestEntity.setParameters(requestElementConverter.convertAllToEntity(requestDTO.getParameters()));

            return requestEntity;
        } else {
            return null;
        }
    }

    @Override
    public RequestDTO convert(final RequestEntity requestEntity) {
        final RequestDTO requestDTO = super.convert(requestEntity);

        if (requestDTO != null) {
            requestDTO.setHeaders(requestElementConverter.convertAllToDTO(requestEntity.getHeaders()));
            requestDTO.setParameters(requestElementConverter.convertAllToDTO(requestEntity.getParameters()));

            return requestDTO;
        } else {
            return null;
        }
    }

    @Override
    public Class<RequestDTO> getDTOClass() {
        return RequestDTO.class;
    }

    @Override
    public Class<RequestEntity> getEntityClass() {
        return RequestEntity.class;
    }

}
