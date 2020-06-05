package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.UserDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.UserRoleDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.dto_service.UserDTOService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class UserDTOServiceTest extends GenericDTOServiceTest<UserDTO, UserEntity, Long> {

    private static final String USER_USER_ROLE_NAME = "USER";

    @Mock
    private GenericRepository<UserEntity, Long> repository;

    private UserDTOService service;

    @Mock
    private UserRoleDTOService userRoleService;

    @BeforeEach
    public void init() {
        final DTOConverter<UserDTO, UserEntity, Long> converter = new UserDTOConverter(new UserRoleDTOConverter());

        service = new UserDTOServiceImpl(repository, converter, userRoleService, new BCryptPasswordEncoder());

        super.init(() -> RequestTestHelper.generateRandomUserDTOs(DEFAULT_DTO_COUNT),
                RandomValueGenerator::generateRandomPositiveLong,
                repository,
                converter,
                service,
                RequestTestHelper::checkUserDTOs);
    }

    @Test
    public void generalTest() {
        createTest();
        readByIdSuccessTest();
        readByIdFailureTest();
        readAllTest();

        updateTest(userDTO -> {
            userDTO.setEmail(RandomValueGenerator.generateRandomStringLettersOnly());

            return userDTO;
        });

        deleteByIdSuccessTest();
        deleteByIdFailureTest();
        existsByIdSuccessTest();
        existsByIdFailureTest();
    }

    @Override
    public void createTest() {
        final UserRoleDTO userRoleDTO =
                RequestTestHelper.generateRandomUserRoleDTOs(1).iterator().next();

        userRoleDTO.setName(USER_USER_ROLE_NAME);

        Mockito.when(userRoleService.readByName(Mockito.anyString())).
                thenReturn(userRoleDTO);

        Mockito.when(repository.save(Mockito.any())).
                thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        final UserDTO userDTO = RequestTestHelper.generateRandomUserDTOs(1).iterator().next();

        final UserDTO persistedUserDTO = service.create(userDTO);

        RequestTestHelper.checkUserDTOs(userDTO, persistedUserDTO);
    }

    @Test
    public void findByEmailSuccessTest() {
        final UserEntity userEntity = RequestTestHelper.generateRandomUserEntities(1).iterator().next();

        final String email = userEntity.getEmail();

        Mockito.when(repository.findAll(Mockito.any())).
                thenReturn(Collections.singletonList(userEntity));

        final UserDTO userDTO = service.readByEmail(email);

        Assertions.assertEquals(email, userDTO.getEmail());
    }

    @Test
    public void findByEmailEmptyFailureTestTest() {
        Mockito.when(repository.findAll(Mockito.any())).
                thenReturn(Collections.emptyList());

        final String email = RandomValueGenerator.generateRandomStringLettersOnly();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readByEmail(email));
    }

    @Test
    public void findByEmailMoreThanOneFailureTest() {
        Mockito.when(repository.findAll(Mockito.any())).
                thenReturn(RequestTestHelper.generateRandomUserEntities(3));

        final String email = RandomValueGenerator.generateRandomStringLettersOnly();

        Assertions.assertThrows(DTOServiceException.class, () -> service.readByEmail(email));
    }

}
