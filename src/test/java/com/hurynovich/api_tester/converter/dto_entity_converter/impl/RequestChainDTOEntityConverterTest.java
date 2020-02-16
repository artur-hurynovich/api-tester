package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestChainEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.test_helper.DTOEntityConverterTestHelper;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class RequestChainDTOEntityConverterTest {

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> elementConverter =
            new NameValueElementDTOEntityConverter();

    private final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter =
            new RequestDTOEntityConverter(elementConverter);

    private final DTOEntityConverter<RequestChainDTO, RequestChainEntity> converter =
            new RequestChainDTOEntityConverter(requestConverter);

    @Test
    public void convertToEntityTest() {
        DTOEntityConverterTestHelper.processConvertToEntityTest(
                () -> RequestTestHelper.generateRandomRequestChainDTOs(1),
                converter,
                RequestTestHelper::checkRequestChainConversion);
    }

    @Test
    public void convertToDTOTest() {
        DTOEntityConverterTestHelper.processConvertToDTOTest(
                () -> RequestTestHelper.generateRandomRequestChainEntities(1),
                converter,
                RequestTestHelper::checkRequestChainConversion);
    }

    @Test
    public void convertAllToEntityTest() {
        DTOEntityConverterTestHelper.processConvertAllToDTOTest(
                () -> RequestTestHelper.generateRandomRequestChainEntities(5),
                converter,
                RequestTestHelper::checkRequestChainConversion);
    }

    @Test
    public void convertAllToDTOTest() {
        DTOEntityConverterTestHelper.processConvertToDTOTest(
                () -> RequestTestHelper.generateRandomRequestChainEntities(5),
                converter,
                RequestTestHelper::checkRequestChainConversion);
    }

}
