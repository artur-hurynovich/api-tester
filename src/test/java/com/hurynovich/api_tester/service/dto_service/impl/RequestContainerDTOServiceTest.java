package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.impl.NameValueElementDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.RequestContainerDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.RequestDTOConverter;
import com.hurynovich.api_tester.mock.MockMongoRepository;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestContainerDocument;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class RequestContainerDTOServiceTest extends GenericDTOServiceTest<RequestContainerDTO, RequestContainerDocument, String> {

    public RequestContainerDTOServiceTest() {
        super(() -> RequestTestHelper.generateRandomRequestContainerDTOs(DEFAULT_DTO_COUNT),
                RandomValueGenerator::generateRandomStringLettersOnly,
                MockMongoRepository::new,
                () -> new RequestContainerDTOConverter(new RequestDTOConverter(new NameValueElementDTOConverter())),
                RequestContainerDTOService::new,
                RequestTestHelper::checkRequestContainer);
    }

    @Test
    public void generalTest() {
        createTest();
        readByIdSuccessTest();
        readByIdFailureTest();
        readAllTest();

        updateTest(requestContainerDTO -> {
            requestContainerDTO.setName(RandomValueGenerator.generateRandomStringLettersOnly());

            return requestContainerDTO;
        });

        deleteByIdSuccessTest();
        deleteByIdFailureTest();
        existsByIdSuccessTest();
        existsByIdFailureTest();
    }

}
