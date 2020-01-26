package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestElementEntity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestElementDTOEntityConverter implements DTOEntityConverter<RequestElementDTO, RequestElementEntity> {

    @Override
    public RequestElementEntity convertToEntity(final RequestElementDTO requestElementDTO) {
        if (requestElementDTO != null) {
            final RequestElementEntity requestElementEntity = new RequestElementEntity();

            BeanUtils.copyProperties(requestElementDTO, requestElementEntity);

            return requestElementEntity;
        } else {
            return null;
        }
    }

    @Override
    public RequestElementDTO convertToDTO(final RequestElementEntity requestElementEntity) {
        if (requestElementEntity != null) {
            final RequestElementDTO requestElementDTO = new RequestElementDTO();

            BeanUtils.copyProperties(requestElementEntity, requestElementDTO);

            return requestElementDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<RequestElementEntity> convertAllToEntity(final Collection<RequestElementDTO> d) {
        if (!CollectionUtils.isEmpty(d)) {
            return d.stream().map(this::convertToEntity).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RequestElementDTO> convertAllToDTO(final Collection<RequestElementEntity> e) {
        if (!CollectionUtils.isEmpty(e)) {
            return e.stream().map(this::convertToDTO).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
