package com.hurynovich.api_tester.security.builder;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsBuilder {

    UserDetails build(UserDTO userDTO);

}
