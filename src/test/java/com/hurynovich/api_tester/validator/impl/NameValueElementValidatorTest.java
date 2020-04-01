package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NameValueElementValidatorTest {

    private final Validator<NameValueElementDTO> nameValueElementValidator =
            new NameValueElementValidator();

    @Test
    public void successValidationTest() {
        final NameValueElementDTO nameValueElement =
                RequestTestHelper.generateRandomNameValueElementDTOs(1).iterator().next();

        final ValidationResult validationResult = nameValueElementValidator.validate(nameValueElement);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void nameValueElementNullFailureValidationTest() {
        final ValidationResult validationResult = nameValueElementValidator.validate(null);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'nameValueElement' can't be null", descriptions.get(0));
    }

    @Test
    public void nameValueElementNameNullFailureValidationTest() {
        final NameValueElementDTO nameValueElement =
                RequestTestHelper.generateRandomNameValueElementDTOs(1).iterator().next();
        nameValueElement.setName(null);

        final ValidationResult validationResult = nameValueElementValidator.validate(nameValueElement);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'nameValueElement.name' can't be null", descriptions.get(0));
    }

    @Test
    public void nameValueElementTypeNullFailureValidationTest() {
        final NameValueElementDTO nameValueElement =
                RequestTestHelper.generateRandomNameValueElementDTOs(1).iterator().next();
        nameValueElement.setType(null);

        final ValidationResult validationResult = nameValueElementValidator.validate(nameValueElement);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'nameValueElement.type' can't be null", descriptions.get(0));
    }

    @Test
    public void nameValueElementValueNullFailureValidationTest() {
        final NameValueElementDTO nameValueElement =
                RequestTestHelper.generateRandomNameValueElementDTOs(1).iterator().next();
        nameValueElement.setType(NameValueElementType.VALUE);
        nameValueElement.setValue(null);

        final ValidationResult validationResult = nameValueElementValidator.validate(nameValueElement);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'nameValueElement.value' can't be null for 'nameValueElement.type' '" +
                NameValueElementType.VALUE + "'", descriptions.get(0));
    }

    @Test
    public void nameValueElementExpressionNullFailureValidationTest() {
        final NameValueElementDTO nameValueElement =
                RequestTestHelper.generateRandomNameValueElementDTOs(1).iterator().next();
        nameValueElement.setType(NameValueElementType.EXPRESSION);
        nameValueElement.setExpression(null);

        final ValidationResult validationResult = nameValueElementValidator.validate(nameValueElement);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'nameValueElement.expression' can't be null for 'nameValueElement.type' '" +
                NameValueElementType.EXPRESSION + "'", descriptions.get(0));
    }

}
