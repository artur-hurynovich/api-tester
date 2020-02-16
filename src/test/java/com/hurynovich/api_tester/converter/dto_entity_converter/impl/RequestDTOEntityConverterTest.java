package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RequestDTOEntityConverterTest {

    private final DTOEntityConverter<RequestElementDTO, RequestElementEntity> elementConverter =
            new RequestElementDTOEntityConverter();

    private final DTOEntityConverter<RequestDTO, RequestEntity> converter =
            new RequestDTOEntityConverter(elementConverter);

    @Test
    public void convertToEntityTest() {
        final RequestDTO requestDTO = RequestTestHelper.generateRandomRequestDTOs(1).iterator().next();

        final RequestEntity requestEntity = converter.convert(requestDTO);

        checkConversion(requestDTO, requestEntity);
    }

    @Test
    public void convertToDTOTest() {
        final RequestEntity requestEntity = RequestTestHelper.
                generateRandomRequestEntities(1).iterator().next();

        final RequestDTO requestDTO = converter.convert(requestEntity);

        checkConversion(requestDTO, requestEntity);
    }

    @Test
    public void convertAllToEntityTest() {
        final List<RequestDTO> requestDTOs = RequestTestHelper.generateRandomRequestDTOs(5);

        final List<RequestEntity> requestEntities = converter.convertAllToEntity(requestDTOs);

        Assertions.assertEquals(requestDTOs.size(), requestEntities.size());
        for (int i = 0; i < requestDTOs.size(); i++) {
            checkConversion(requestDTOs.get(i), requestEntities.get(i));
        }
    }

    @Test
    public void convertAllToDTOTest() {
        final List<RequestEntity> requestEntities = RequestTestHelper.generateRandomRequestEntities(5);

        final List<RequestDTO> requestDTOs = converter.convertAllToDTO(requestEntities);

        Assertions.assertEquals(requestEntities.size(), requestDTOs.size());
        for (int i = 0; i < requestEntities.size(); i++) {
            checkConversion(requestDTOs.get(i), requestEntities.get(i));
        }
    }

    private void checkConversion(final RequestDTO dto, final RequestEntity entity) {
        Assertions.assertEquals(dto.getMethod(), entity.getMethod());

        final List<RequestElementDTO> dtoHeaders = dto.getHeaders();
        final List<RequestElementEntity> entityHeaders = entity.getHeaders();
        Assertions.assertEquals(dtoHeaders.size(), entityHeaders.size());
        for (int i = 0; i < dtoHeaders.size(); i++) {
            checkRequestElementConversion(dtoHeaders.get(i), entityHeaders.get(i));
        }

        Assertions.assertEquals(dto.getUrl(), entity.getUrl());

        final List<RequestElementDTO> dtoParameters = dto.getParameters();
        final List<RequestElementEntity> entityParameters = entity.getParameters();
        Assertions.assertEquals(dtoParameters.size(), entityParameters.size());
        for (int i = 0; i < dtoParameters.size(); i++) {
            checkRequestElementConversion(dtoParameters.get(i), entityParameters.get(i));
        }

        Assertions.assertEquals(dto.getBody(), entity.getBody());
    }

    private void checkRequestElementConversion(final RequestElementDTO dto, final RequestElementEntity entity) {
        Assertions.assertEquals(dto.getName(), entity.getName());
        Assertions.assertEquals(dto.getValue(), entity.getValue());
        Assertions.assertEquals(dto.getExpression(), entity.getExpression());
        Assertions.assertEquals(dto.getType(), entity.getType());
    }

}
