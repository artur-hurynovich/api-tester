package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.impl.ExecutionLogDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.ExecutionLogEntryDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.NameValueElementDTOConverter;
import com.hurynovich.api_tester.mock.MockMongoRepository;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class ExecutionLogDTOServiceTest extends GenericDTOServiceTest<ExecutionLogDTO, ExecutionLogDocument, String> {

    public ExecutionLogDTOServiceTest() {
        super(() -> RequestTestHelper.generateRandomExecutionLogDTOs(DEFAULT_DTO_COUNT),
                RandomValueGenerator::generateRandomStringLettersOnly,
                MockMongoRepository::new,
                () -> new ExecutionLogDTOConverter(new ExecutionLogEntryDTOConverter(new NameValueElementDTOConverter())),
                ExecutionLogDTOService::new,
                RequestTestHelper::checkExecutionLog);
    }

    @Test
    public void generalTest() {
        createTest();
        readByIdSuccessTest();
        readByIdFailureTest();
        readAllTest();

        updateTest(executionLogDTO -> {
            executionLogDTO.setUserId(RandomValueGenerator.generateRandomPositiveLong());

            return executionLogDTO;
        });

        deleteByIdSuccessTest();
        deleteByIdFailureTest();
        existsByIdSuccessTest();
        existsByIdFailureTest();
    }

}
