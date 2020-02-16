package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.test_helper.DTOEntityConverterTestHelper;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class NameValueElementDTOEntityConverterTest {

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> converter =
            new RequestElementDTOEntityConverter();

    @Test
    public void convertToEntityTest() {
        DTOEntityConverterTestHelper.processConvertToEntityTest(
                () -> RequestTestHelper.generateRandomNameValueElementDTOs(1),
                converter,
                RequestTestHelper::checkNameValueElementConversion);
    }

    @Test
    public void convertToDTOTest() {
        DTOEntityConverterTestHelper.processConvertToDTOTest(
                () -> RequestTestHelper.generateRandomNameValueElementEntities(1),
                converter,
                RequestTestHelper::checkNameValueElementConversion);
    }

    @Test
    public void convertAllToEntityTest() {
        DTOEntityConverterTestHelper.processConvertAllToEntityTest(
                () -> RequestTestHelper.generateRandomNameValueElementDTOs(5),
                converter,
                RequestTestHelper::checkNameValueElementConversion);
    }

    @Test
    public void convertAllToDTOTest() {
        DTOEntityConverterTestHelper.processConvertAllToDTOTest(
                () -> RequestTestHelper.generateRandomNameValueElementEntities(5),
                converter,
                RequestTestHelper::checkNameValueElementConversion);
    }

}
