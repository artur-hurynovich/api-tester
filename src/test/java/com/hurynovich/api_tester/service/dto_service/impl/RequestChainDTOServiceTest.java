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
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

public class RequestChainDTOServiceTest {

    private final JpaRepository<RequestChainEntity, Long> repository = new MockJpaRepository<>();

    private final DTOEntityConverter<NameValueElementDTO, NameValueElementEntity> nameValueElementConverter =
            new NameValueElementDTOEntityConverter();
    private final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter =
            new RequestDTOEntityConverter(nameValueElementConverter);
    private final DTOEntityConverter<RequestChainDTO, RequestChainEntity> requestChainConverter =
            new RequestChainDTOEntityConverter(requestConverter);

    private GenericDTOService<RequestChainDTO, RequestChainEntity, Long> service =
            new RequestChainDTOService(repository, requestChainConverter);

    @BeforeEach
    private void clearRepository() {
        repository.deleteAll();
    }

    @Test
    public void createTest() {
        final RequestChainDTO requestChain =
                RequestTestHelper.generateRandomRequestChainDTOs(1).iterator().next();

        final RequestChainDTO storedRequestChain = service.create(requestChain);

        checkRequestChain(requestChain, storedRequestChain);
    }

    @Test
    public void readByIdSuccessTest() {
        final RequestChainDTO requestChain = RequestTestHelper.generateRandomRequestChainDTOs(1).iterator().next();

        initRepository(Collections.singletonList(requestChain));

        final RequestChainDTO storedRequestChain = service.readById(requestChain.getId());

        checkRequestChain(requestChain, storedRequestChain);
    }

    @Test
    public void readByIdFailureTest() {
        final long id = RandomValueGenerator.generateRandomPositiveInt();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readById(id));
    }

    @Test
    public void readAllTest() {
        final List<RequestChainDTO> requestChains = RequestTestHelper.generateRandomRequestChainDTOs(3);

        initRepository(requestChains);

        final List<RequestChainDTO> storedRequestChains = service.readAll();

        Assertions.assertEquals(requestChains.size(), storedRequestChains.size());

        for (int i = 0; i < requestChains.size(); i++) {
            final RequestChainDTO requestChain = requestChains.get(i);
            final RequestChainDTO storedRequestChain = storedRequestChains.get(i);

            checkRequestChain(requestChain, storedRequestChain);
        }
    }

    @Test
    public void updateTest() {
        final RequestChainDTO requestChain = RequestTestHelper.generateRandomRequestChainDTOs(1).iterator().next();

        initRepository(Collections.singletonList(requestChain));

        requestChain.getRequests().clear();
        requestChain.setRequests(RequestTestHelper.generateRandomRequestDTOs(2));

        service.update(requestChain);

        final RequestChainDTO storedRequestChain = service.readById(requestChain.getId());

        checkRequestChain(requestChain, storedRequestChain);
    }

    @Test
    public void deleteByIdSuccessTest() {
        final RequestChainDTO requestChain = RequestTestHelper.generateRandomRequestChainDTOs(1).iterator().next();

        initRepository(Collections.singletonList(requestChain));

        service.deleteById(requestChain.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readById(requestChain.getId()));
    }

    @Test
    public void deleteByIdFailureTest() {
        final long id = RandomValueGenerator.generateRandomPositiveInt();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(id));
    }

    @Test
    public void existsByIdSuccessTest() {
        final RequestChainDTO requestChain = RequestTestHelper.generateRandomRequestChainDTOs(1).iterator().next();

        initRepository(Collections.singletonList(requestChain));

        Assertions.assertTrue(service.existsById(requestChain.getId()));
    }

    @Test
    public void existsByIdFailureTest() {
        final long id = RandomValueGenerator.generateRandomPositiveInt();

        Assertions.assertFalse(service.existsById(id));
    }

    private void initRepository(final List<RequestChainDTO> requestChains) {
        final List<RequestChainEntity> requestChainEntities =
                requestChainConverter.convertAllToEntity(requestChains);

        requestChainEntities.forEach(repository::save);
    }

    private void checkRequestChain(final RequestChainDTO expected, final RequestChainDTO actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());

        final List<RequestDTO> expectedRequests = expected.getRequests();
        final List<RequestDTO> actualRequests = actual.getRequests();

        Assertions.assertEquals(expectedRequests.size(), actualRequests.size());

        for (int i = 0; i < expectedRequests.size(); i++) {
            final RequestDTO expectedRequest = expectedRequests.get(i);
            final RequestDTO actualRequest = actualRequests.get(i);

            checkRequests(expectedRequest, actualRequest);
        }
    }

    private void checkRequests(final RequestDTO expectedRequest, final RequestDTO actualRequest) {
        Assertions.assertEquals(expectedRequest.getId(), actualRequest.getId());

        final List<NameValueElementDTO> expectedHeaders = expectedRequest.getHeaders();
        final List<NameValueElementDTO> actualHeaders = actualRequest.getHeaders();

        Assertions.assertEquals(expectedHeaders.size(), actualHeaders.size());

        for (int i = 0; i < expectedHeaders.size(); i++) {
            final NameValueElementDTO expectedHeader = expectedHeaders.get(i);
            final NameValueElementDTO actualHeader = actualHeaders.get(i);

            checkNameValueElement(expectedHeader, actualHeader);
        }

        Assertions.assertEquals(expectedRequest.getUrl(), actualRequest.getUrl());

        final List<NameValueElementDTO> expectedParameters = expectedRequest.getParameters();
        final List<NameValueElementDTO> actualParameters = actualRequest.getParameters();

        Assertions.assertEquals(expectedParameters.size(), actualParameters.size());

        for (int i = 0; i < expectedHeaders.size(); i++) {
            final NameValueElementDTO expectedParameter = expectedParameters.get(i);
            final NameValueElementDTO actualParameter = actualParameters.get(i);

            checkNameValueElement(expectedParameter, actualParameter);
        }

        Assertions.assertEquals(expectedRequest.getBody(), actualRequest.getBody());
    }

    private void checkNameValueElement(final NameValueElementDTO expectedNameValueElement,
                                       final NameValueElementDTO actualNameValueElement) {
        Assertions.assertEquals(expectedNameValueElement.getName(), actualNameValueElement.getName());
        Assertions.assertEquals(expectedNameValueElement.getValue(), actualNameValueElement.getValue());
        Assertions.assertEquals(expectedNameValueElement.getExpression(), actualNameValueElement.getExpression());
        Assertions.assertEquals(expectedNameValueElement.getType(), actualNameValueElement.getType());
    }

}
