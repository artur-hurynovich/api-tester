package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class RequestContainerValidatorTest {

    final Validator<NameValueElementDTO> nameValueElementValidator = new NameValueElementValidator();

    final Validator<RequestDTO> requestValidator = new RequestValidator(nameValueElementValidator);

    final Validator<RequestContainerDTO> requestContainerValidator = new RequestContainerValidator(requestValidator);

    @Test
    public void successValidationTest() {
        final RequestContainerDTO requestContainer =
                RequestTestHelper.generateRandomRequestContainerDTOs(1).iterator().next();

        final ValidationResult validationResult = requestContainerValidator.validate(requestContainer);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void requestContainerNullFailureValidationTest() {
        final ValidationResult validationResult = requestContainerValidator.validate(null);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestContainer' can't be null", descriptions.get(0));
    }

    @Test
    public void requestContainerNameNullFailureValidationTest() {
        final RequestContainerDTO requestContainer =
                RequestTestHelper.generateRandomRequestContainerDTOs(1).iterator().next();
        requestContainer.setName(null);

        final ValidationResult validationResult = requestContainerValidator.validate(requestContainer);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestContainer.name' can't be null", descriptions.get(0));
    }

    @Test
    public void requestContainerRequestsNullFailureValidationTest() {
        final RequestContainerDTO requestContainer =
                RequestTestHelper.generateRandomRequestContainerDTOs(1).iterator().next();
        requestContainer.setRequests(null);

        final ValidationResult validationResult = requestContainerValidator.validate(requestContainer);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestContainer.requests' can't be null", descriptions.get(0));
    }

    @Test
    public void requestContainerRequestsEmptyFailureValidationTest() {
        final RequestContainerDTO requestContainer =
                RequestTestHelper.generateRandomRequestContainerDTOs(1).iterator().next();
        requestContainer.setRequests(Collections.emptyList());

        final ValidationResult validationResult = requestContainerValidator.validate(requestContainer);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestContainer.requests' can't be empty", descriptions.get(0));
    }

}
