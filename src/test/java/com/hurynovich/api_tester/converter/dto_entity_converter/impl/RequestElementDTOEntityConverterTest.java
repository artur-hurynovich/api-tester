package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestElementEntity;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RequestElementDTOEntityConverterTest {

    private final DTOEntityConverter<RequestElementDTO, RequestElementEntity> converter =
            new RequestElementDTOEntityConverter();

    @Test
    public void convertToEntityTest() {
        final List<RequestElementDTO> requestElements = RequestTestHelper.generateRandomRequestElementDTOs(1);

        final RequestElementDTO requestElementDTO = requestElements.iterator().next();

        final RequestElementEntity requestElementEntity = converter.convertToEntity(requestElementDTO);

        checkConversion(requestElementDTO, requestElementEntity);
    }

    @Test
    public void convertToDTOTest() {
        final List<RequestElementEntity> requestElements = RequestTestHelper.generateRandomRequestElementEntities(1);

        final RequestElementEntity requestElementEntity = requestElements.iterator().next();

        final RequestElementDTO requestElementDTO = converter.convertToDTO(requestElementEntity);

        checkConversion(requestElementDTO, requestElementEntity);
    }

    @Test
    public void convertAllToEntityTest() {
        final List<RequestElementDTO> requestElements = RequestTestHelper.generateRandomRequestElementDTOs(5);

        final List<RequestElementEntity> requestElementEntities = converter.convertAllToEntity(requestElements);

        Assertions.assertEquals(requestElements.size(), requestElementEntities.size());
        for (int i = 0; i < requestElements.size(); i++) {
            checkConversion(requestElements.get(i), requestElementEntities.get(i));
        }
    }

    @Test
    public void convertAllToDTOTest() {
        final List<RequestElementEntity> requestElements = RequestTestHelper.generateRandomRequestElementEntities(5);

        final List<RequestElementDTO> requestElementDTOS = converter.convertAllToDTO(requestElements);

        Assertions.assertEquals(requestElements.size(), requestElementDTOS.size());
        for (int i = 0; i < requestElements.size(); i++) {
            checkConversion(requestElementDTOS.get(i), requestElements.get(i));
        }
    }

    private void checkConversion(final RequestElementDTO dto, final RequestElementEntity entity) {
        Assertions.assertEquals(dto.getName(), entity.getName());
        Assertions.assertEquals(dto.getValue(), entity.getValue());
        Assertions.assertEquals(dto.getExpression(), entity.getExpression());
        Assertions.assertEquals(dto.getType(), entity.getType());
    }

}
