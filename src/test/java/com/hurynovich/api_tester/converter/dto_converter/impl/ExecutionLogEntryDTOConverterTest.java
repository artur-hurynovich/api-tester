package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogEntryDocument;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class ExecutionLogEntryDTOConverterTest extends GenericDTOConverterTest<ExecutionLogEntryDTO, ExecutionLogEntryDocument, String> {

    public ExecutionLogEntryDTOConverterTest() {
        super(() -> RequestTestHelper.generateRandomExecutionLogEntryDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomExecutionLogEntryDocuments(DEFAULT_CONVERTIBLE_COUNT),
                () -> new ExecutionLogEntryDTOConverter(new NameValueElementDTOConverter()),
                RequestTestHelper::checkExecutionLogEntryConversion);
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
