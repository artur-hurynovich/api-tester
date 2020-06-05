package com.hurynovich.api_tester.validator.impl.authentication_request_validator;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.security.model.AuthenticationRequest;
import com.hurynovich.api_tester.service.dto_service.UserDTOService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service("authenticationRequestOnRegistrationValidator")
public class AuthenticationRequestOnRegistrationValidator implements Validator<AuthenticationRequest> {

    private final Validator<AuthenticationRequest> authenticationRequestOnLoginValidator;

    private final UserDTOService userService;

    public AuthenticationRequestOnRegistrationValidator(final @NonNull @Qualifier("authenticationRequestOnLoginValidator") Validator<AuthenticationRequest> authenticationRequestOnLoginValidator,
                                                        final @NonNull UserDTOService userService) {
        this.authenticationRequestOnLoginValidator = authenticationRequestOnLoginValidator;
        this.userService = userService;
    }

    @Override
    public ValidationResult validate(final AuthenticationRequest authenticationRequest) {
        final ValidationResult validationResult = authenticationRequestOnLoginValidator.validate(authenticationRequest);

        if (validationResult.getType() == ValidationResultType.VALID) {
            validateEmailUniqueness(authenticationRequest, validationResult);
        }

        return validationResult;
    }

    private void validateEmailUniqueness(final @NonNull AuthenticationRequest authenticationRequest,
                                         final @NonNull ValidationResult validationResult) {
        final String email = authenticationRequest.getEmail();

        final UserDTO userDTO = userService.readByEmailQuite(email);

        if (userDTO != null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("User with email '" + email + "' already exists");
        }
    }

}
