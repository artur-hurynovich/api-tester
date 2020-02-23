package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.test_helper.DTOEntityConverterTestHelper;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiConsumer;

public class RequestDTOEntityConverterTest {

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> elementConverter =
            new NameValueElementDTOEntityConverter();

    private final DTOEntityConverter<RequestDTO, RequestEntity> converter =
            new RequestDTOEntityConverter(elementConverter);

    private final BiConsumer<RequestDTO, RequestEntity> checkConsumer =
            RequestTestHelper::checkRequestConversion;

    private final DTOEntityConverterTestHelper<RequestDTO, RequestEntity> helper =
            new DTOEntityConverterTestHelper<>(converter, checkConsumer);

    @Test
    public void convertToEntityTest() {
        final List<RequestDTO> requests = RequestTestHelper.generateRandomRequestDTOs(1);

        helper.processConvertToEntityTest(requests);
    }

    @Test
    public void convertToDTOTest() {
        final List<RequestEntity> requests = RequestTestHelper.generateRandomRequestEntities(1);

        helper.processConvertToDTOTest(requests);
    }

    @Test
    public void convertAllToEntityTest() {
        final List<RequestDTO> requests = RequestTestHelper.generateRandomRequestDTOs(5);

        helper.processConvertAllToEntityTest(requests);
    }

    @Test
    public void convertAllToDTOTest() {
        final List<RequestEntity> requests = RequestTestHelper.generateRandomRequestEntities(5);

        helper.processConvertAllToDTOTest(requests);
    }

}
