package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;

public interface UserDTOService extends DTOService<UserDTO, Long> {

    UserDTO readByEmail(String email);

}
