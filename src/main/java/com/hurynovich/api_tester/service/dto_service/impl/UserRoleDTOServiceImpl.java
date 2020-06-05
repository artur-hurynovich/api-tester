package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserRoleEntity;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;
import com.hurynovich.api_tester.service.dto_service.UserRoleDTOService;
import com.hurynovich.api_tester.service.exception.DTOServiceException;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserRoleDTOServiceImpl extends GenericDTOService<UserRoleDTO, UserRoleEntity, Long>
        implements UserRoleDTOService {

    private static final String NAME_FIELD_NAME = "name";

    public UserRoleDTOServiceImpl(final @NonNull GenericRepository<UserRoleEntity, Long> repository,
                                  final @NonNull DTOConverter<UserRoleDTO, UserRoleEntity, Long> converter) {
        super(repository, converter);
    }

    @Transactional
    @Override
    public UserRoleDTO readByName(final @NonNull String name) {
        final UserRoleEntity userRoleEntity = instantiatePersistentObject();

        userRoleEntity.setName(name);

        final List<UserRoleDTO> userRoleDTOs = readByExample(Example.of(userRoleEntity));

        if (userRoleDTOs.isEmpty()) {
            throw new EntityNotFoundException(buildEntityNotFoundExceptionMessage(NAME_FIELD_NAME, name));
        } else if (userRoleDTOs.size() > 1) {
            throw new DTOServiceException(buildMoreThanOneEntityFoundExceptionMessage(NAME_FIELD_NAME, name));
        } else {
            return userRoleDTOs.iterator().next();
        }
    }

    @Override
    protected Class<UserRoleEntity> getPersistentObjectClass() {
        return UserRoleEntity.class;
    }

}
