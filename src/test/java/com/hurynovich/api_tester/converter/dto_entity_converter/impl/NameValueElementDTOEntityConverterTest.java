package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class NameValueElementDTOEntityConverterTest
        extends GenericDTOEntityConverterTest<NameValueElementDTO, NameValueElementEntity> {

    public NameValueElementDTOEntityConverterTest() {
        super(() -> RequestTestHelper.generateRandomNameValueElementDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomNameValueElementEntities(DEFAULT_ENTITY_COUNT),
                NameValueElementDTOEntityConverter::new,
                RequestTestHelper::checkNameValueElementConversion);
    }

    @Test
    public void convertToEntityTest() {
        super.convertToEntityTest();
    }

    @Test
    public void convertToDTOTest() {
        super.convertToDTOTest();
    }

    @Test
    public void convertAllToEntityTest() {
        super.convertAllToEntityTest();
    }

    @Test
    public void convertAllToDTOTest() {
        super.convertAllToDTOTest();
    }

}
