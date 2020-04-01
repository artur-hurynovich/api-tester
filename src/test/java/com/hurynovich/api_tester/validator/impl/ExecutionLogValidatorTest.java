package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ExecutionLogValidatorTest {

    private final Validator<NameValueElementDTO> nameValueElementValidator =
            new NameValueElementValidator();

    private final Validator<ExecutionLogEntryDTO> executionLogEntryValidator =
            new ExecutionLogEntryValidator(nameValueElementValidator);

    @Mock
    private DTOService<UserDTO, Long> userService;

    private Validator<ExecutionLogDTO> executionLogValidator;

    @BeforeEach
    public void init() {
        executionLogValidator = new ExecutionLogValidator(userService, executionLogEntryValidator);
    }

    @Test
    public void successValidationTest() {
        final ExecutionLogDTO executionLog =
                RequestTestHelper.generateRandomExecutionLogDTOs(1).iterator().next();

        Mockito.when(userService.existsById(executionLog.getUserId())).thenReturn(true);

        final ValidationResult validationResult = executionLogValidator.validate(executionLog);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void executionLogNullFailureValidationTest() {
        final ValidationResult validationResult = executionLogValidator.validate(null);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLog' can't be null", descriptions.get(0));
    }

    @Test
    public void executionLogEntryDateTimeNullFailureValidationTest() {
        final ExecutionLogDTO executionLog =
                RequestTestHelper.generateRandomExecutionLogDTOs(1).iterator().next();
        executionLog.setDateTime(null);

        Mockito.when(userService.existsById(executionLog.getUserId())).thenReturn(true);

        final ValidationResult validationResult = executionLogValidator.validate(executionLog);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLog.dateTime' can't be null", descriptions.get(0));
    }

    @Test
    public void executionLogDateTimeLaterThanNowFailureValidationTest() {
        final ExecutionLogDTO executionLog =
                RequestTestHelper.generateRandomExecutionLogDTOs(1).iterator().next();
        executionLog.setDateTime(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1L));

        Mockito.when(userService.existsById(executionLog.getUserId())).thenReturn(true);

        final ValidationResult validationResult = executionLogValidator.validate(executionLog);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLog.dateTime' can't be later than now", descriptions.get(0));
    }

    @Test
    public void executionLogUserIdNullFailureValidationTest() {
        final ExecutionLogDTO executionLog =
                RequestTestHelper.generateRandomExecutionLogDTOs(1).iterator().next();
        executionLog.setUserId(null);

        final ValidationResult validationResult = executionLogValidator.validate(executionLog);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLog.userId' can't be null", descriptions.get(0));
    }

    @Test
    public void executionLogUserIdNotFoundFailureValidationTest() {
        final ExecutionLogDTO executionLog =
                RequestTestHelper.generateRandomExecutionLogDTOs(1).iterator().next();

        Mockito.when(userService.existsById(executionLog.getUserId())).thenReturn(false);

        final ValidationResult validationResult = executionLogValidator.validate(executionLog);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("No user found for 'executionLog.userId' '" + executionLog.getUserId() + "'",
                descriptions.get(0));
    }

    @Test
    public void executionLogEntriesNullFailureValidationTest() {
        final ExecutionLogDTO executionLog =
                RequestTestHelper.generateRandomExecutionLogDTOs(1).iterator().next();
        executionLog.setEntries(null);

        Mockito.when(userService.existsById(executionLog.getUserId())).thenReturn(true);

        final ValidationResult validationResult = executionLogValidator.validate(executionLog);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLog.entries' can't be null", descriptions.get(0));
    }

    @Test
    public void executionLogEntriesEmptyFailureValidationTest() {
        final ExecutionLogDTO executionLog =
                RequestTestHelper.generateRandomExecutionLogDTOs(1).iterator().next();
        executionLog.setEntries(Collections.emptyList());

        Mockito.when(userService.existsById(executionLog.getUserId())).thenReturn(true);

        final ValidationResult validationResult = executionLogValidator.validate(executionLog);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionLog.entries' can't be empty", descriptions.get(0));
    }

}
