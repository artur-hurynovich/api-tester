package com.hurynovich.api_tester.security.service.impl;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.security.builder.impl.UserDetailsBuilderImpl;
import com.hurynovich.api_tester.service.dto_service.UserDTOService;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserDTOService userService;

    @Test
    public void loadUserByUsernameTest() {
        final UserDetailsService userDetailsService =
                new UserDetailsServiceImpl(userService, new UserDetailsBuilderImpl());

        final String email = RandomValueGenerator.generateRandomStringLettersOnly();

        final UserDTO userDTO = RequestTestHelper.generateRandomUserDTOs(1).iterator().next();

        userDTO.setEmail(email);

        Mockito.when(userService.readByEmail(email)).thenReturn(userDTO);

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(email);

        RequestTestHelper.checkUserDetails(userDTO, userDetails);
    }

}
