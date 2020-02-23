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

import java.util.List;
import java.util.function.BiConsumer;

public class RequestChainDTOEntityConverterTest {

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> elementConverter =
            new NameValueElementDTOEntityConverter();

    private final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter =
            new RequestDTOEntityConverter(elementConverter);

    private final DTOEntityConverter<RequestChainDTO, RequestChainEntity> converter =
            new RequestChainDTOEntityConverter(requestConverter);

    private final BiConsumer<RequestChainDTO, RequestChainEntity> checkConsumer =
            RequestTestHelper::checkRequestChainConversion;

    private final DTOEntityConverterTestHelper<RequestChainDTO, RequestChainEntity> helper =
            new DTOEntityConverterTestHelper<>(converter, checkConsumer);

    @Test
    public void convertToEntityTest() {
        final List<RequestChainDTO> requestChains = RequestTestHelper.generateRandomRequestChainDTOs(1);

        helper.processConvertToEntityTest(requestChains);
    }

    @Test
    public void convertToDTOTest() {
        final List<RequestChainEntity> requestChains = RequestTestHelper.generateRandomRequestChainEntities(1);

        helper.processConvertToDTOTest(requestChains);
    }

    @Test
    public void convertAllToEntityTest() {
        final List<RequestChainDTO> requestChains = RequestTestHelper.generateRandomRequestChainDTOs(1);

        helper.processConvertAllToEntityTest(requestChains);
    }

    @Test
    public void convertAllToDTOTest() {
        final List<RequestChainEntity> requestChains = RequestTestHelper.generateRandomRequestChainEntities(5);

        helper.processConvertAllToDTOTest(requestChains);
    }

}
