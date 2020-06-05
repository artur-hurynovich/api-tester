package com.hurynovich.api_tester.security.service;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {

    String buildToken(UserDTO userDTO);

    Authentication getAuthentication(String token);

    String fetchToken(HttpServletRequest request);

    boolean validateToken(String token);

}
