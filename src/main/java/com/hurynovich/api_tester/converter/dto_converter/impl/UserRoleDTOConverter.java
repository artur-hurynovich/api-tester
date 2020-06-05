package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.GenericDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserRoleEntity;
import org.springframework.stereotype.Service;

@Service
public class UserRoleDTOConverter extends GenericDTOConverter<UserRoleDTO, UserRoleEntity, Long> {

    private static final String[] IGNORE_PROPERTIES = {"users"};

    public UserRoleDTOConverter() {
        super(IGNORE_PROPERTIES);
    }

    @Override
    public Class<UserRoleDTO> getDTOClass() {
        return UserRoleDTO.class;
    }

    @Override
    public Class<UserRoleEntity> getPersistentObjectClass() {
        return UserRoleEntity.class;
    }

}
