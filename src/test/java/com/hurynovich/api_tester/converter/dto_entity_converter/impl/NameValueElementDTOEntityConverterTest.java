package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.test_helper.DTOEntityConverterTestHelper;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiConsumer;

public class NameValueElementDTOEntityConverterTest {

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> converter =
            new NameValueElementDTOEntityConverter();

    private final BiConsumer<NameValueElementDTO, NameValueElementEntity> checkConsumer =
            RequestTestHelper::checkNameValueElementConversion;

    private final DTOEntityConverterTestHelper<NameValueElementDTO, NameValueElementEntity> helper =
            new DTOEntityConverterTestHelper<>(converter, checkConsumer);

    @Test
    public void convertToEntityTest() {
        final List<NameValueElementDTO> nameValueElements =
                RequestTestHelper.generateRandomNameValueElementDTOs(1);

        helper.processConvertToEntityTest(nameValueElements);
    }

    @Test
    public void convertToDTOTest() {
        final List<NameValueElementEntity> nameValueElements =
                RequestTestHelper.generateRandomNameValueElementEntities(1);

        helper.processConvertToDTOTest(nameValueElements);
    }

    @Test
    public void convertAllToEntityTest() {
        final List<NameValueElementDTO> nameValueElements =
                RequestTestHelper.generateRandomNameValueElementDTOs(5);

        helper.processConvertAllToEntityTest(nameValueElements);
    }

    @Test
    public void convertAllToDTOTest() {
        final List<NameValueElementEntity> nameValueElements =
                RequestTestHelper.generateRandomNameValueElementEntities(5);

        helper.processConvertAllToDTOTest(nameValueElements);
    }

}
