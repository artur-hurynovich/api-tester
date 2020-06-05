package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserRoleEntity;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;

public class UserRoleDTOConverterTest extends GenericDTOConverterTest<UserRoleDTO, UserRoleEntity, Long> {

    public UserRoleDTOConverterTest() {
        super(() -> RequestTestHelper.generateRandomUserRoleDTOs(DEFAULT_DTO_COUNT),
                () -> RequestTestHelper.generateRandomUserRoleEntities(DEFAULT_CONVERTIBLE_COUNT),
                UserRoleDTOConverter::new,
                RequestTestHelper::checkUserRolesConversion);
    }

    @Test
    public void generalTest() {
        convertFromDTOTest();
        convertFromNullDTOTest();
        convertToDTOTest();
        convertFromNullToDTOTest();
        convertAllFromDTOTest();
        convertAllFromNullDTOTest();
        convertAllToDTOTest();
        convertAllFromNullToDTOTest();
    }

}
