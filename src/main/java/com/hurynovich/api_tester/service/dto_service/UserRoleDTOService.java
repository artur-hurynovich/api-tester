package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;

public interface UserRoleDTOService extends DTOService<UserRoleDTO, Long> {

    UserRoleDTO readByName(String name);

}
