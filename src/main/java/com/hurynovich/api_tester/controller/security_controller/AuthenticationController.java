package com.hurynovich.api_tester.controller.security_controller;

import com.hurynovich.api_tester.model.controller_response.impl.AuthenticationControllerResponse;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.security.model.AuthenticationRequest;
import com.hurynovich.api_tester.security.service.JwtService;
import com.hurynovich.api_tester.service.dto_service.UserDTOService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final Validator<AuthenticationRequest> authenticationRequestValidator;

    private final AuthenticationManager authenticationManager;

    private final UserDTOService userService;

    private final JwtService jwtService;

    public AuthenticationController(final @NonNull Validator<AuthenticationRequest> authenticationRequestValidator,
                                    final @NonNull AuthenticationManager authenticationManager,
                                    final @NonNull UserDTOService userService,
                                    final @NonNull JwtService jwtService) {
        this.authenticationRequestValidator = authenticationRequestValidator;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationControllerResponse> login(final @NonNull @RequestBody AuthenticationRequest authenticationRequest) {
        final String email = authenticationRequest.getEmail();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, authenticationRequest.getPassword()));

        final ValidationResult validationResult = authenticationRequestValidator.validate(authenticationRequest);

        final AuthenticationControllerResponse response = new AuthenticationControllerResponse();
        response.setValidationResult(validationResult);

        if (validationResult.getType() == ValidationResultType.VALID) {
            response.setEmail(email);

            final UserDTO userDTO = userService.readByEmail(email);

            final String token = jwtService.buildToken(userDTO);

            response.setToken(token);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
