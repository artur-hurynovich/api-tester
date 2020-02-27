package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.converter.dto_entity_converter.impl.NameValueElementDTOEntityConverter;
import com.hurynovich.api_tester.converter.dto_entity_converter.impl.RequestChainDTOEntityConverter;
import com.hurynovich.api_tester.converter.dto_entity_converter.impl.RequestDTOEntityConverter;
import com.hurynovich.api_tester.mock.MockJpaRepository;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestChainEntity;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Test;

public class RequestChainDTOServiceTest extends GenericDTOServiceTest<RequestChainDTO, RequestChainEntity> {

    public RequestChainDTOServiceTest() {
        super(() -> RequestTestHelper.generateRandomRequestChainDTOs(DEFAULT_DTO_COUNT),
                MockJpaRepository::new,
                () -> {
                    final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> nameValueElementConverter =
                            new NameValueElementDTOEntityConverter();

                    final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter =
                            new RequestDTOEntityConverter(nameValueElementConverter);

                    return new RequestChainDTOEntityConverter(requestConverter);
                },
                RequestChainDTOService::new,
                RequestTestHelper::checkRequestChain);
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
        super.updateTest(requestChain -> {
            requestChain.getRequests().clear();
            requestChain.setRequests(RequestTestHelper.generateRandomRequestDTOs(3));

            return requestChain;
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
