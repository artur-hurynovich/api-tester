package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.NameValueElementDocument;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class NameValueElementDTOConverterTest extends GenericDTOConverterTest<NameValueElementDTO, NameValueElementDocument, String> {

    public NameValueElementDTOConverterTest() {
        super(() -> RequestTestHelper.generateRandomNameValueElementDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomNameValueElementDocuments(DEFAULT_CONVERTIBLE_COUNT),
                NameValueElementDTOConverter::new,
                RequestTestHelper::checkNameValueElementConversion);
    }

    @Test
    public void generalTest() {
        convertFromDTOTest();
        convertFromNullDTOTest();
        convertToDTOTest();
        convertFromNullToDTOTest();
        convertAllFromDTOTest();
        convertAllFromNullDTOTest();
        convertAllToDTOTest();
        convertAllFromNullToDTOTest();
    }

}
