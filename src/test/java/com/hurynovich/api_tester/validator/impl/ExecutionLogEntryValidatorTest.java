package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

public class ExecutionLogEntryValidatorTest {

    private final Validator<NameValueElementDTO> nameValueElementValidator =
            new NameValueElementValidator();

    private final Validator<ExecutionLogEntryDTO> executionLogEntryValidator =
            new ExecutionLogEntryValidator(nameValueElementValidator);

    @Test
    public void successValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomExecutionLogEntries(1).iterator().next();

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void executionLogEntryNullFailureValidationTest() {
        final ValidationResult validationResult = executionLogEntryValidator.validate(null);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry' can't be null", descriptions.get(0));
    }

    @Test
    public void executionLogEntryTypeNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomExecutionLogEntries(1).iterator().next();
        executionLogEntry.setType(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.type' can't be null", descriptions.get(0));
    }

    @Test
    public void executionLogEntryDateTimeNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomExecutionLogEntries(1).iterator().next();
        executionLogEntry.setDateTime(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.dateTime' can't be null", descriptions.get(0));
    }

    @Test
    public void executionLogEntryDateTimeLaterThanNowFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomExecutionLogEntries(1).iterator().next();
        executionLogEntry.setDateTime(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1L));

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.dateTime' can't be later than now", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryMethodNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomRequestExecutionLogEntries(1).iterator().next();
        executionLogEntry.setMethod(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.method' can't be null for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryHeadersNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomRequestExecutionLogEntries(1).iterator().next();
        executionLogEntry.setHeaders(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.headers' can't be null for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryHeadersEmptyFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomRequestExecutionLogEntries(1).iterator().next();
        executionLogEntry.setHeaders(Collections.emptyList());

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.headers' can't be empty for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryParametersNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomRequestExecutionLogEntries(1).iterator().next();
        executionLogEntry.setParameters(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.parameters' can't be null for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryParametersEmptyFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomRequestExecutionLogEntries(1).iterator().next();
        executionLogEntry.setParameters(Collections.emptyList());

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.parameters' can't be empty for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryUrlNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomRequestExecutionLogEntries(1).iterator().next();
        executionLogEntry.setUrl(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.url' can't be null for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryStatusNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomResponseExecutionLogEntries(1).iterator().next();
        executionLogEntry.setStatus(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.status' can't be null for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryBodyNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomRequestExecutionLogEntries(1).iterator().next();
        executionLogEntry.setBody(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.body' can't be null for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

    @Test
    public void requestExecutionLogEntryErrorMessageNullFailureValidationTest() {
        final ExecutionLogEntryDTO executionLogEntry =
                RequestTestHelper.generateRandomErrorExecutionLogEntries(1).iterator().next();
        executionLogEntry.setErrorMessage(null);

        final ValidationResult validationResult = executionLogEntryValidator.validate(executionLogEntry);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLogEntry.errorMessage' can't be null for 'executionLogEntry.type' '" +
                executionLogEntry.getType() + "'", descriptions.get(0));
    }

}
