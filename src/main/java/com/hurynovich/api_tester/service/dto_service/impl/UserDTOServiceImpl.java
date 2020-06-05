package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.dto_service.GenericDTOService;
import com.hurynovich.api_tester.service.dto_service.UserDTOService;
import com.hurynovich.api_tester.service.dto_service.UserRoleDTOService;
import com.hurynovich.api_tester.service.exception.DTOServiceException;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class UserDTOServiceImpl extends GenericDTOService<UserDTO, UserEntity, Long>
        implements UserDTOService {

    private static final String USER_USER_ROLE_NAME = "USER";

    private static final String EMAIL_FIELD_NAME = "email";

    private final UserRoleDTOService userRoleService;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserDTOServiceImpl(final @NonNull GenericRepository<UserEntity, Long> repository,
                              final @NonNull DTOConverter<UserDTO, UserEntity, Long> converter,
                              final @NonNull UserRoleDTOService userRoleService,
                              final @NonNull BCryptPasswordEncoder passwordEncoder) {
        super(repository, converter);

        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDTO create(final @NonNull UserDTO userDTO) {
        applyDefaultUserRolesOnCreation(userDTO);

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return super.create(userDTO);
    }

    private void applyDefaultUserRolesOnCreation(final @NonNull UserDTO userDTO) {
        final UserRoleDTO defaultUserRoles = userRoleService.readByName(USER_USER_ROLE_NAME);

        userDTO.setRoles(Collections.singletonList(defaultUserRoles));
    }

    @Transactional
    @Override
    public UserDTO readByEmail(final @NonNull String email) {
        final UserEntity userEntity = instantiatePersistentObject();

        userEntity.setEmail(email);

        final List<UserDTO> userDTOs = readByExample(Example.of(userEntity));

        if (userDTOs.isEmpty()) {
            throw new EntityNotFoundException(buildEntityNotFoundExceptionMessage(EMAIL_FIELD_NAME, email));
        } else if (userDTOs.size() > 1) {
            throw new DTOServiceException(buildMoreThanOneEntityFoundExceptionMessage(EMAIL_FIELD_NAME, email));
        } else {
            return userDTOs.iterator().next();
        }
    }

    @Transactional
    @Override
    public UserDTO update(final @NonNull UserDTO userDTO) {
        return super.create(userDTO);
    }

    @Override
    protected Class<UserEntity> getPersistentObjectClass() {
        return UserEntity.class;
    }

}
