package com.hurynovich.api_tester.validator.impl.execution_signal_validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.ValidatorTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.hurynovich.api_tester.model.enumeration.ValidationResultType.NON_VALID;
import static com.hurynovich.api_tester.model.enumeration.ValidationResultType.VALID;

@ExtendWith(MockitoExtension.class)
public class ControllerExecutionSignalValidatorTest {

    @Mock
    private Validator<ExecutionCacheKey> keyValidator;

    @Mock
    private ExecutionHelper executionHelper;

    @Mock
    private ExecutionState executionState;

    private Validator<ExecutionSignal> signalValidator;

    @BeforeEach
    public void init() {
        signalValidator = new ControllerExecutionSignalValidator(keyValidator, executionHelper);
    }

    @Test
    public void validSignalTest() {
        final String randomSignalName = ExecutionTestHelper.getRandomSignalName();

        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(randomSignalName);

        final ValidationResult keyValidationResult = ValidatorTestHelper.buildValidValidationResult();
        Mockito.when(keyValidator.validate(signal.getExecutionCacheKey())).thenReturn(keyValidationResult);

        Mockito.when(executionHelper.getExecutionState(signal.getExecutionCacheKey())).thenReturn(executionState);
        Mockito.when(executionHelper.resolveValidSignalNamesOnInit(executionState)).
                thenReturn(Collections.singletonList(randomSignalName));

        final ValidationResult validationResult = signalValidator.validate(signal);
        Assertions.assertEquals(VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertTrue(descriptions.isEmpty());
    }

    @Test
    public void nonValidSignalTest() {
        final String randomSignalName = ExecutionTestHelper.getRandomSignalName();

        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(randomSignalName);

        final ValidationResult keyValidationResult = ValidatorTestHelper.buildValidValidationResult();
        Mockito.when(keyValidator.validate(signal.getExecutionCacheKey())).thenReturn(keyValidationResult);

        Mockito.when(executionHelper.getExecutionState(signal.getExecutionCacheKey())).thenReturn(executionState);
        Mockito.when(executionHelper.resolveValidSignalNamesOnInit(executionState)).
                thenReturn(Collections.emptyList());

        final ValidationResult validationResult = signalValidator.validate(signal);
        Assertions.assertEquals(NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'" + randomSignalName + "' is not a valid 'executionSignal.signal.signalName'",
                descriptions.get(0));
    }

}
