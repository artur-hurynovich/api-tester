package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class ExecutionLogDTOConverterTest extends GenericDTOConverterTest<ExecutionLogDTO, ExecutionLogDocument, String> {

    public ExecutionLogDTOConverterTest() {
        super(() -> RequestTestHelper.generateRandomExecutionLogDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomExecutionLogDocuments(DEFAULT_CONVERTIBLE_COUNT),
                () -> new ExecutionLogDTOConverter(new ExecutionLogEntryDTOConverter(new NameValueElementDTOConverter())),
                RequestTestHelper::checkExecutionLogConversion);
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
