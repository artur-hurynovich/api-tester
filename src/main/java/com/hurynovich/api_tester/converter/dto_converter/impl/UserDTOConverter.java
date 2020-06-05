package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.converter.dto_converter.GenericDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserRoleEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserDTOConverter extends GenericDTOConverter<UserDTO, UserEntity, Long> {

    private static final String[] IGNORE_PROPERTIES = {"roles"};

    private final DTOConverter<UserRoleDTO, UserRoleEntity, Long> userRoleConverter;

    public UserDTOConverter(final @NonNull DTOConverter<UserRoleDTO, UserRoleEntity, Long> userRoleConverter) {
        super(IGNORE_PROPERTIES);

        this.userRoleConverter = userRoleConverter;
    }

    @Override
    public UserEntity convert(final UserDTO userDTO) {
        final UserEntity userEntity = super.convert(userDTO);

        if (userEntity != null) {
            userEntity.setRoles(userRoleConverter.convertAllFromDTO(userDTO.getRoles()));
        }

        return userEntity;
    }

    @Override
    public UserDTO convert(final UserEntity userEntity) {
        final UserDTO userDTO = super.convert(userEntity);

        if (userDTO != null) {
            userDTO.setRoles(userRoleConverter.convertAllToDTO(userEntity.getRoles()));
        }

        return userDTO;
    }

    @Override
    public Class<UserDTO> getDTOClass() {
        return UserDTO.class;
    }

    @Override
    public Class<UserEntity> getPersistentObjectClass() {
        return UserEntity.class;
    }

}
