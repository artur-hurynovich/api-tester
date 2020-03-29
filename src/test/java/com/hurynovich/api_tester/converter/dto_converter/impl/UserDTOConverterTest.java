package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class UserDTOConverterTest extends GenericDTOConverterTest<UserDTO, UserEntity, Long> {

    public UserDTOConverterTest() {
        super(() -> RequestTestHelper.generateRandomUserDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomUserEntities(DEFAULT_CONVERTIBLE_COUNT),
                UserDTOConverter::new,
                RequestTestHelper::checkUserConversion);
    }

    @Test
    public void generalTest() {
        convertToEntityTest();
        convertToDTOTest();
        convertAllToEntityTest();
        convertAllToDTOTest();
    }

}
