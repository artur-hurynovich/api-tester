package com.hurynovich.api_tester.security.service.impl;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.security.builder.UserDetailsBuilder;
import com.hurynovich.api_tester.service.dto_service.UserDTOService;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDTOService userService;

    private final UserDetailsBuilder userDetailsBuilder;

    public UserDetailsServiceImpl(final @NonNull UserDTOService userService,
                                  final @NonNull UserDetailsBuilder userDetailsBuilder) {
        this.userService = userService;
        this.userDetailsBuilder = userDetailsBuilder;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        final UserDTO userDTO = userService.readByEmail(email);

        return userDetailsBuilder.build(userDTO);
    }

}
