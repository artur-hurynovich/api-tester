package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.entity.impl.UserEntity;
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserDTOService extends GenericDTOService<UserDTO, UserEntity, Long> {

    public UserDTOService(final @NonNull JpaRepository<UserEntity, Long> repository,
                          final @NonNull DTOEntityConverter<UserDTO, UserEntity> converter) {
        super(repository, converter);
    }

}
