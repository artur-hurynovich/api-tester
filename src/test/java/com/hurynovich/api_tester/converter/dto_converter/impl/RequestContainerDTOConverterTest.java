package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestContainerDocument;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class RequestContainerDTOConverterTest extends GenericDTOConverterTest<RequestContainerDTO, RequestContainerDocument, String> {

    public RequestContainerDTOConverterTest() {
        super(() -> RequestTestHelper.generateRandomRequestContainerDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomRequestContainerDocuments(DEFAULT_CONVERTIBLE_COUNT),
                () -> new RequestContainerDTOConverter(new RequestDTOConverter(new NameValueElementDTOConverter())),
                RequestTestHelper::checkRequestContainerConversion);
    }

    @Test
    public void generalTest() {
        convertToEntityTest();
        convertToDTOTest();
        convertAllToEntityTest();
        convertAllToDTOTest();
    }

}
