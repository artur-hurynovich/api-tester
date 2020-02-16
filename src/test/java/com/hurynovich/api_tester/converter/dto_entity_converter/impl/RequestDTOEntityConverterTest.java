package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.test_helper.DTOEntityConverterTestHelper;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class RequestDTOEntityConverterTest {

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> elementConverter =
            new NameValueElementDTOEntityConverter();

    private final DTOEntityConverter<RequestDTO, RequestEntity> converter =
            new RequestDTOEntityConverter(elementConverter);

    @Test
    public void convertToEntityTest() {
        DTOEntityConverterTestHelper.processConvertToEntityTest(
                () -> RequestTestHelper.generateRandomRequestDTOs(1),
                converter,
                RequestTestHelper::checkRequestConversion);
    }

    @Test
    public void convertToDTOTest() {
        DTOEntityConverterTestHelper.processConvertToDTOTest(
                () -> RequestTestHelper.generateRandomRequestEntities(1),
                converter,
                RequestTestHelper::checkRequestConversion);
    }

    @Test
    public void convertAllToEntityTest() {
        DTOEntityConverterTestHelper.processConvertAllToEntityTest(
                () -> RequestTestHelper.generateRandomRequestDTOs(5),
                converter,
                RequestTestHelper::checkRequestConversion);
    }

    @Test
    public void convertAllToDTOTest() {
        DTOEntityConverterTestHelper.processConvertAllToDTOTest(
                () -> RequestTestHelper.generateRandomRequestEntities(5),
                converter,
                RequestTestHelper::checkRequestConversion);
    }

}
