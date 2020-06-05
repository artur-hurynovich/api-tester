package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.security.model.AuthenticationRequest;
import com.hurynovich.api_tester.validator.Validator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationRequestValidator implements Validator<AuthenticationRequest> {

    private static final int PASSWORD_MINIMAL_LENGTH = 5;

    @Override
    public ValidationResult validate(final AuthenticationRequest authenticationRequest) {
        final ValidationResult validationResult = ValidationResult.createValidResult();

        if (authenticationRequest == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'authenticationRequest' can't be null");
        } else {
            validateEmail(authenticationRequest, validationResult);

            validatePassword(authenticationRequest, validationResult);
        }

        return validationResult;
    }

    private void validateEmail(final @NonNull AuthenticationRequest authenticationRequest,
                               final @NonNull ValidationResult validationResult) {
        final String email = authenticationRequest.getEmail();

        if (email == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'authenticationRequest.email' can't be null");
        } else if (!EmailValidator.getInstance().isValid(email)) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'" + email + "' is not a valid 'authenticationRequest.email'");
        }
    }

    private void validatePassword(final @NonNull AuthenticationRequest authenticationRequest,
                                  final @NonNull ValidationResult validationResult) {
        final String password = authenticationRequest.getPassword();

        if (password == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'authenticationRequest.password' can't be null");
        } else if (password.length() < PASSWORD_MINIMAL_LENGTH) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'authenticationRequest.password' length can't be less than " +
                    PASSWORD_MINIMAL_LENGTH + " symbols");
        }
    }

}
