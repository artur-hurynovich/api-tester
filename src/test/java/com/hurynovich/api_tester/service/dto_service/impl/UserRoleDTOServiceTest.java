package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.UserRoleDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserRoleEntity;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.dto_service.UserRoleDTOService;
import com.hurynovich.api_tester.service.exception.DTOServiceException;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class UserRoleDTOServiceTest extends GenericDTOServiceTest<UserRoleDTO, UserRoleEntity, Long> {

    @Mock
    private GenericRepository<UserRoleEntity, Long> repository;

    private UserRoleDTOService service;

    @BeforeEach
    public void init() {
        final DTOConverter<UserRoleDTO, UserRoleEntity, Long> converter =
                new UserRoleDTOConverter();

        service = new UserRoleDTOServiceImpl(repository, converter);

        super.init(() -> RequestTestHelper.generateRandomUserRoleDTOs(DEFAULT_DTO_COUNT),
                RandomValueGenerator::generateRandomPositiveLong,
                repository,
                converter,
                service,
                RequestTestHelper::checkUserRoleDTOs);
    }

    @Test
    public void generalTest() {
        createTest();
        readByIdSuccessTest();
        readByIdFailureTest();
        readAllTest();

        updateTest(userRoleDTO -> {
            userRoleDTO.setName(RandomValueGenerator.generateRandomStringLettersOnly());

            return userRoleDTO;
        });

        deleteByIdSuccessTest();
        deleteByIdFailureTest();
        existsByIdSuccessTest();
        existsByIdFailureTest();
    }

    @Test
    public void findByNameSuccessTest() {
        final UserRoleEntity userRoleEntity = RequestTestHelper.generateRandomUserRoleEntities(1).iterator().next();

        final String name = userRoleEntity.getName();

        Mockito.when(repository.findAll(Mockito.any())).
                thenReturn(Collections.singletonList(userRoleEntity));

        final UserRoleDTO userRoleDTO = service.readByName(name);

        Assertions.assertEquals(name, userRoleDTO.getName());
    }

    @Test
    public void findByNameEmptyFailureTestTest() {
        Mockito.when(repository.findAll(Mockito.any())).
                thenReturn(Collections.emptyList());

        final String name = RandomValueGenerator.generateRandomStringLettersOnly();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readByName(name));
    }

    @Test
    public void findByNameMoreThanOneFailureTest() {
        Mockito.when(repository.findAll(Mockito.any())).
                thenReturn(RequestTestHelper.generateRandomUserRoleEntities(3));

        final String name = RandomValueGenerator.generateRandomStringLettersOnly();

        Assertions.assertThrows(DTOServiceException.class, () -> service.readByName(name));
    }

}
