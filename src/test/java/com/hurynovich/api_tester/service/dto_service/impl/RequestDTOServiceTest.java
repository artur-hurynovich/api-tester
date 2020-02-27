package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.converter.dto_entity_converter.impl.NameValueElementDTOEntityConverter;
import com.hurynovich.api_tester.converter.dto_entity_converter.impl.RequestDTOEntityConverter;
import com.hurynovich.api_tester.mock.MockJpaRepository;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class RequestDTOServiceTest extends GenericDTOServiceTest<RequestDTO, RequestEntity> {

    public RequestDTOServiceTest() {
        super(() -> RequestTestHelper.generateRandomRequestDTOs(DEFAULT_DTO_COUNT),
                MockJpaRepository::new,
                () -> {
                    final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> nameValueElementConverter =
                            new NameValueElementDTOEntityConverter();

                    return new RequestDTOEntityConverter(nameValueElementConverter);
                },
                RequestDTOService::new,
                RequestTestHelper::checkRequest);
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
    public void readAllTest() {
        super.readAllTest();
    }

    @Test
    public void updateTest() {
        super.updateTest(request -> {
            request.setUrl(RequestTestHelper.generateRandomHttpUrl());
            request.setBody(RequestTestHelper.generateRandomBody());

            return request;
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

    @Test
    public void existsByIdSuccessTest() {
        super.existsByIdSuccessTest();
    }

    @Test
    public void existsByIdFailureTest() {
        super.existsByIdFailureTest();
    }

}
