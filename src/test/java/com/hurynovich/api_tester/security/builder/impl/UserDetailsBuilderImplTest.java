package com.hurynovich.api_tester.security.builder.impl;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.security.builder.UserDetailsBuilder;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsBuilderImplTest {

    private final UserDetailsBuilder userDetailsBuilder = new UserDetailsBuilderImpl();

    @Test
    public void buildTest() {
        final UserDTO userDTO = RequestTestHelper.generateRandomUserDTOs(1).iterator().next();

        final UserDetails userDetails = userDetailsBuilder.build(userDTO);

        RequestTestHelper.checkUserDetails(userDTO, userDetails);
    }

}
