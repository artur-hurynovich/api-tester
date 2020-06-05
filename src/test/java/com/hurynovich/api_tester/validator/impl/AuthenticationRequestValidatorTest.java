package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.security.model.AuthenticationRequest;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AuthenticationRequestValidatorTest {

    private static final int PASSWORD_MINIMAL_LENGTH = 5;

    private final Validator<AuthenticationRequest> validator =
            new AuthenticationRequestValidator();

    @Test
    public void successValidationTest() {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        authenticationRequest.setEmail(RequestTestHelper.generateRandomEmail());
        authenticationRequest.setPassword(RandomValueGenerator.generateRandomStringLettersOnly());

        final ValidationResult validationResult = validator.validate(authenticationRequest);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void authenticationRequestNullFailureValidationTest() {
        final ValidationResult validationResult = validator.validate(null);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'authenticationRequest' can't be null", descriptions.get(0));
    }

    @Test
    public void authenticationRequestEmailNullFailureValidationTest() {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        authenticationRequest.setPassword(RandomValueGenerator.generateRandomStringLettersOnly());

        final ValidationResult validationResult = validator.validate(authenticationRequest);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'authenticationRequest.email' can't be null", descriptions.get(0));
    }

    @Test
    public void authenticationRequestEmailNonValidFailureValidationTest() {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        final String email = RandomValueGenerator.generateRandomStringLettersOnly();
        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(RandomValueGenerator.generateRandomStringLettersOnly());

        final ValidationResult validationResult = validator.validate(authenticationRequest);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'" + email + "' is not a valid 'authenticationRequest.email'",
                descriptions.get(0));
    }

    @Test
    public void authenticationRequestPasswordNullFailureValidationTest() {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        authenticationRequest.setEmail(RequestTestHelper.generateRandomEmail());

        final ValidationResult validationResult = validator.validate(authenticationRequest);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'authenticationRequest.password' can't be null", descriptions.get(0));
    }

    @Test
    public void authenticationRequestPasswordNonValidFailureValidationTest() {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        authenticationRequest.setEmail(RequestTestHelper.generateRandomEmail());
        authenticationRequest.setPassword(RandomValueGenerator.generateRandomStringLettersOnly().
                substring(0, PASSWORD_MINIMAL_LENGTH - 1));

        final ValidationResult validationResult = validator.validate(authenticationRequest);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'authenticationRequest.password' length can't be less than " +
                PASSWORD_MINIMAL_LENGTH + " symbols", descriptions.get(0));
    }

}
