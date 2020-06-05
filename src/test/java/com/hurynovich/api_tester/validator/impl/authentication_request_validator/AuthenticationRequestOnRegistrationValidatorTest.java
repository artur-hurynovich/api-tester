package com.hurynovich.api_tester.validator.impl.authentication_request_validator;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.security.model.AuthenticationRequest;
import com.hurynovich.api_tester.service.dto_service.UserDTOService;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AuthenticationRequestOnRegistrationValidatorTest {

    @Mock
    private Validator<AuthenticationRequest> authenticationRequestOnLoginValidator;

    @Mock
    private UserDTOService userService;

    private Validator<AuthenticationRequest> authenticationRequestOnRegistrationValidator;

    @BeforeEach
    public void init() {
        authenticationRequestOnRegistrationValidator =
                new AuthenticationRequestOnRegistrationValidator(authenticationRequestOnLoginValidator, userService);
    }

    @Test
    public void successValidationTest() {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        final String email = RequestTestHelper.generateRandomEmail();

        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(RandomValueGenerator.generateRandomStringLettersOnly());

        Mockito.when(authenticationRequestOnLoginValidator.validate(authenticationRequest)).
                thenReturn(ValidationResult.createValidResult());

        Mockito.when(userService.readByEmailQuite(email)).thenReturn(null);

        final ValidationResult validationResult = authenticationRequestOnRegistrationValidator.
                validate(authenticationRequest);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void userWithEmailAlreadyExistsFailureTest() {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        final String email = RequestTestHelper.generateRandomEmail();

        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(RandomValueGenerator.generateRandomStringLettersOnly());

        Mockito.when(authenticationRequestOnLoginValidator.validate(authenticationRequest)).
                thenReturn(ValidationResult.createValidResult());

        final UserDTO userDTO = RequestTestHelper.generateRandomUserDTOs(1).iterator().next();

        Mockito.when(userService.readByEmailQuite(email)).thenReturn(userDTO);

        final ValidationResult validationResult = authenticationRequestOnRegistrationValidator.
                validate(authenticationRequest);

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("User with email '" + email + "' already exists", descriptions.get(0));
    }

}
