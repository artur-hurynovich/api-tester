package com.hurynovich.api_tester.security.builder.impl;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.model.enumeration.Status;
import com.hurynovich.api_tester.security.builder.UserDetailsBuilder;
import com.hurynovich.api_tester.security.model.UserDetailsImpl;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsBuilderImpl implements UserDetailsBuilder {

    @Override
    public UserDetails build(final @NonNull UserDTO userDTO) {
        final List<GrantedAuthority> grantedAuthorities =
                convertUserRolesToGrantedAuthorities(userDTO.getRoles());

        return new UserDetailsImpl(grantedAuthorities, userDTO.getPassword(), userDTO.getEmail(),
                userDTO.getStatus() == Status.ACTIVE);
    }

    private List<GrantedAuthority> convertUserRolesToGrantedAuthorities(final @NonNull List<UserRoleDTO> userRoleDTOs) {
        return userRoleDTOs.stream().
                map(userRoleDTO -> new SimpleGrantedAuthority(userRoleDTO.getName())).
                collect(Collectors.toList());
    }

}
