package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.impl.ExecutionLogDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.ExecutionLogEntryDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.NameValueElementDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExecutionLogDTOServiceTest extends GenericDTOServiceTest<ExecutionLogDTO, ExecutionLogDocument, String> {

    @Mock
    private GenericRepository<ExecutionLogDocument, String> repository;

    @BeforeEach
    public void init() {
        super.init(() -> RequestTestHelper.generateRandomExecutionLogDTOs(DEFAULT_DTO_COUNT),
                RandomValueGenerator::generateRandomStringLettersOnly,
                repository,
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
