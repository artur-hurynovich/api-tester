package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.GenericDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserDTOConverter extends GenericDTOConverter<UserDTO, UserEntity, Long> {

    @Override
    public Class<UserDTO> getDTOClass() {
        return UserDTO.class;
    }

    @Override
    public Class<UserEntity> getPersistentObjectClass() {
        return UserEntity.class;
    }

}
