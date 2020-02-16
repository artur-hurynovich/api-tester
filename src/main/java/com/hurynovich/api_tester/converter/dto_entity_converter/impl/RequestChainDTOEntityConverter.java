package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.converter.dto_entity_converter.GeneticDTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestChainEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestChainDTOEntityConverter extends GeneticDTOEntityConverter<RequestChainDTO, RequestChainEntity> {

    private static final String[] IGNORE_PROPERTIES = {"requests"};

    private final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter;

    public RequestChainDTOEntityConverter(final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter) {
        super(IGNORE_PROPERTIES);

        this.requestConverter = requestConverter;
    }

    @Override
    public RequestChainEntity convert(final RequestChainDTO requestChainDTO) {
        final RequestChainEntity requestChainEntity = super.convert(requestChainDTO);

        if (requestChainEntity != null) {
            final List<RequestEntity> requestEntities = requestConverter.
                    convertAllToEntity(requestChainDTO.getRequests());

            requestChainEntity.setRequests(requestEntities);

            return requestChainEntity;
        } else {
            return null;
        }
    }

    @Override
    public RequestChainDTO convert(final RequestChainEntity requestChainEntity) {
        final RequestChainDTO requestChainDTO = super.convert(requestChainEntity);

        if (requestChainDTO != null) {
            final List<RequestDTO> requestDTOs = requestConverter.
                    convertAllToDTO(requestChainEntity.getRequests());

            requestChainDTO.setRequests(requestDTOs);

            return requestChainDTO;
        } else {
            return null;
        }
    }

    @Override
    public Class<RequestChainDTO> getDTOClass() {
        return RequestChainDTO.class;
    }

    @Override
    public Class<RequestChainEntity> getEntityClass() {
        return RequestChainEntity.class;
    }

}
