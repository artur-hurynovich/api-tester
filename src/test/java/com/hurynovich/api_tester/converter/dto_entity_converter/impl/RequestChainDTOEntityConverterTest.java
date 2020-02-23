package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestChainEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class RequestChainDTOEntityConverterTest
        extends GenericDTOEntityConverterTest<RequestChainDTO, RequestChainEntity> {

    public RequestChainDTOEntityConverterTest() {
        super(() -> RequestTestHelper.generateRandomRequestChainDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomRequestChainEntities(DEFAULT_ENTITY_COUNT),
                () -> {
                    final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> elementConverter =
                            new NameValueElementDTOEntityConverter();

                    final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter =
                            new RequestDTOEntityConverter(elementConverter);

                    return new RequestChainDTOEntityConverter(requestConverter);
                },
                RequestTestHelper::checkRequestChainConversion);
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
