package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.impl.UserDTOConverter;
import com.hurynovich.api_tester.mock.MockJpaRepository;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class UserDTOServiceTest extends GenericDTOServiceTest<UserDTO, UserEntity, Long> {

    public UserDTOServiceTest() {
        super(() -> RequestTestHelper.generateRandomUserDTOs(DEFAULT_DTO_COUNT),
                RandomValueGenerator::generateRandomPositiveLong,
                MockJpaRepository::new,
                UserDTOConverter::new,
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
            userDTO.setName(RandomValueGenerator.generateRandomStringLettersOnly());

            return userDTO;
        });

        deleteByIdSuccessTest();
        deleteByIdFailureTest();
        existsByIdSuccessTest();
        existsByIdFailureTest();
    }

}
