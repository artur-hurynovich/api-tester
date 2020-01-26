package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestDTOEntityConverter implements DTOEntityConverter<RequestDTO, RequestEntity> {

    private static final String[] IGNORE_PROPERTIES = {"headers", "parameters"};

    private final DTOEntityConverter<RequestElementDTO, RequestElementEntity> requestElementConverter;

    public RequestDTOEntityConverter(final DTOEntityConverter<RequestElementDTO, RequestElementEntity> requestElementConverter) {
        this.requestElementConverter = requestElementConverter;
    }

    @Override
    public RequestEntity convertToEntity(final RequestDTO requestDTO) {
        if (requestDTO != null) {
            final RequestEntity requestEntity = new RequestEntity();

            BeanUtils.copyProperties(requestDTO, requestEntity, IGNORE_PROPERTIES);

            requestEntity.setHeaders(requestElementConverter.convertAllToEntity(requestDTO.getHeaders()));
            requestEntity.setParameters(requestElementConverter.convertAllToEntity(requestDTO.getParameters()));

            return requestEntity;
        } else {
            return null;
        }
    }

    @Override
    public RequestDTO convertToDTO(final RequestEntity requestEntity) {
        if (requestEntity != null) {
            final RequestDTO requestDTO = new RequestDTO();

            BeanUtils.copyProperties(requestEntity, requestDTO, IGNORE_PROPERTIES);

            requestDTO.setHeaders(requestElementConverter.convertAllToDTO(requestEntity.getHeaders()));
            requestEntity.setParameters(requestElementConverter.convertAllToEntity(requestDTO.getParameters()));

            return requestDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<RequestEntity> convertAllToEntity(final Collection<RequestDTO> d) {
        if (!CollectionUtils.isEmpty(d)) {
            return d.stream().map(this::convertToEntity).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RequestDTO> convertAllToDTO(final Collection<RequestEntity> e) {
        if (!CollectionUtils.isEmpty(e)) {
            return e.stream().map(this::convertToDTO).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
