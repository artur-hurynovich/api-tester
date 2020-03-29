package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestDocument;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class RequestDTOConverterTest extends GenericDTOConverterTest<RequestDTO, RequestDocument, String> {

    public RequestDTOConverterTest() {
        super(() -> RequestTestHelper.generateRandomRequestDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomRequestDocuments(DEFAULT_CONVERTIBLE_COUNT),
                () -> new RequestDTOConverter(new NameValueElementDTOConverter()),
                RequestTestHelper::checkRequestConversion);
    }

    @Test
    public void generalTest() {
        convertToEntityTest();
        convertToDTOTest();
        convertAllToEntityTest();
        convertAllToDTOTest();
    }

}
