package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RequestValidatorTest {

    final Validator<NameValueElementDTO> nameValueElementValidator = new NameValueElementValidator();

    final Validator<RequestDTO> requestValidator = new RequestValidator(nameValueElementValidator);

    @Test
    public void successValidationTest() {
        final RequestDTO request = RequestTestHelper.generateRandomRequestDTOs(1).iterator().next();

        final ValidationResult validationResult = requestValidator.validate(request);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void requestNullFailureValidationTest() {
        final ValidationResult validationResult = requestValidator.validate(null);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'request' can't be null", descriptions.get(0));
    }

    @Test
    public void requestMethodNullFailureValidationTest() {
        final RequestDTO request = RequestTestHelper.generateRandomRequestDTOs(1).iterator().next();
        request.setMethod(null);

        final ValidationResult validationResult = requestValidator.validate(request);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'request.method' can't be null", descriptions.get(0));
    }

    @Test
    public void requestUrlNullFailureValidationTest() {
        final RequestDTO request = RequestTestHelper.generateRandomRequestDTOs(1).iterator().next();
        request.setUrl(null);

        final ValidationResult validationResult = requestValidator.validate(request);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'request.url' can't be null", descriptions.get(0));
    }

    @Test
    public void requestUrlNonValidFailureValidationTest() {
        final RequestDTO request = RequestTestHelper.generateRandomRequestDTOs(1).iterator().next();
        final String url = RandomValueGenerator.generateRandomStringLettersOnly(10);
        request.setUrl(url);

        final ValidationResult validationResult = requestValidator.validate(request);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'" + url + "' is not a valid 'request.url'", descriptions.get(0));
    }

}
