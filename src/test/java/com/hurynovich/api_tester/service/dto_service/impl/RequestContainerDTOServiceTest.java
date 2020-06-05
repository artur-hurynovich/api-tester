package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.impl.NameValueElementDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.RequestContainerDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.RequestDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.RequestContainerDocument;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RequestContainerDTOServiceTest extends GenericDTOServiceTest<RequestContainerDTO, RequestContainerDocument, String> {

    @Mock
    private GenericRepository<RequestContainerDocument, String> repository;

    @BeforeEach
    public void init() {
        final RequestContainerDTOConverter converter =
                new RequestContainerDTOConverter(new RequestDTOConverter(new NameValueElementDTOConverter()));

        final DTOService<RequestContainerDTO, String> service = new RequestContainerDTOService(repository, converter);

        super.init(() -> RequestTestHelper.generateRandomRequestContainerDTOs(DEFAULT_DTO_COUNT),
                RandomValueGenerator::generateRandomStringLettersOnly,
                repository,
                converter,
                service,
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
