package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserDTOService extends GenericDTOService<UserDTO, UserEntity, Long> {

    public UserDTOService(final @NonNull CrudRepository<UserEntity, Long> repository,
                          final @NonNull DTOConverter<UserDTO, UserEntity, Long> converter) {
        super(repository, converter);
    }

}
