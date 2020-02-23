package com.hurynovich.api_tester.service.document_service.impl;

import com.hurynovich.api_tester.mock.MockMongoRepository;
import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class ExecutionLogDocumentServiceTest extends GenericDocumentServiceTest<ExecutionLogDocument> {

    public ExecutionLogDocumentServiceTest() {
        super(() -> RequestTestHelper.generateRandomExecutionLogDocuments(DEFAULT_DTO_COUNT),
                MockMongoRepository::new,
                ExecutionLogDocumentService::new,
                RequestTestHelper::checkExecutionLog);
    }

    @Test
    public void createTest() {
        super.createTest();
    }

    @Test
    public void readByIdSuccessTest() {
        super.readByIdSuccessTest();
    }

    @Test
    public void readByIdFailureTest() {
        super.readByIdFailureTest();
    }

    @Test
    public void updateTest() {
        super.updateTest((executionLog) -> {
            executionLog.getEntries().clear();
            executionLog.setEntries(RequestTestHelper.generateRandomExecutionLogEntries(3));

            return executionLog;
        });
    }

    @Test
    public void deleteByIdSuccessTest() {
        super.deleteByIdSuccessTest();
    }

    @Test
    public void deleteByIdFailureTest() {
        super.deleteByIdFailureTest();
    }

}
