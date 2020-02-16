package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.converter.dto_entity_converter.GeneticDTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseDTOEntityConverter extends GeneticDTOEntityConverter<ResponseDTO, ResponseEntity> {

    private static final String[] IGNORE_PROPERTIES = {"headers"};

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> requestElementConverter;

    public ResponseDTOEntityConverter(final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> requestElementConverter) {
        super(IGNORE_PROPERTIES);

        this.requestElementConverter = requestElementConverter;
    }

    @Override
    public ResponseEntity convert(final ResponseDTO responseDTO) {
        final ResponseEntity responseEntity = super.convert(responseDTO);

        if (responseEntity != null) {
            final List<NameValueElementDTO> dtoHeaders = responseDTO.getHeaders();

            responseEntity.setHeaders(requestElementConverter.convertAllToEntity(dtoHeaders));

            return responseEntity;
        } else {
            return null;
        }
    }

    @Override
    public ResponseDTO convert(final ResponseEntity responseEntity) {
        final ResponseDTO responseDTO = super.convert(responseEntity);

        if (responseDTO != null) {
            final List<NameValueElementEntity> entityHeaders = responseEntity.getHeaders();

            responseDTO.setHeaders(requestElementConverter.convertAllToDTO(entityHeaders));

            return responseDTO;
        } else {
            return null;
        }
    }

    @Override
    public Class<ResponseDTO> getDTOClass() {
        return ResponseDTO.class;
    }

    @Override
    public Class<ResponseEntity> getEntityClass() {
        return ResponseEntity.class;
    }

}
