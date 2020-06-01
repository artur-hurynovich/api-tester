package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.impl.UserDTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.impl.UserRoleDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDTOServiceTest extends GenericDTOServiceTest<UserDTO, UserEntity, Long> {

    @Mock
    private GenericRepository<UserEntity, Long> repository;

    @BeforeEach
    public void init() {
        super.init(() -> RequestTestHelper.generateRandomUserDTOs(DEFAULT_DTO_COUNT),
                RandomValueGenerator::generateRandomPositiveLong,
                repository,
                () -> new UserDTOConverter(new UserRoleDTOConverter()),
                UserDTOService::new,
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

}
