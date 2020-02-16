package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.ResponseEntity;
import com.hurynovich.api_tester.test_helper.DTOEntityConverterTestHelper;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class ResponseDTOEntityConverterTest {

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> requestConverter =
            new RequestElementDTOEntityConverter();

    private final DTOEntityConverter<ResponseDTO, ResponseEntity> converter =
            new ResponseDTOEntityConverter(requestConverter);

    @Test
    public void convertToEntityTest() {
        DTOEntityConverterTestHelper.processConvertToEntityTest(
                () -> RequestTestHelper.generateRandomResponseDTOs(1),
                converter,
                RequestTestHelper::checkResponseConversion);
    }

    @Test
    public void convertToDTOTest() {
        DTOEntityConverterTestHelper.processConvertToDTOTest(
                () -> RequestTestHelper.generateRandomResponseEntities(1),
                converter,
                RequestTestHelper::checkResponseConversion);
    }

    @Test
    public void convertAllToEntityTest() {
        DTOEntityConverterTestHelper.processConvertAllToEntityTest(
                () -> RequestTestHelper.generateRandomResponseDTOs(5),
                converter,
                RequestTestHelper::checkResponseConversion);
    }

    @Test
    public void convertAllToDTOTest() {
        DTOEntityConverterTestHelper.processConvertAllToDTOTest(
                () -> RequestTestHelper.generateRandomResponseEntities(5),
                converter,
                RequestTestHelper::checkResponseConversion);
    }

}
