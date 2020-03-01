package com.hurynovich.api_tester.validator.execution_signal_validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.service.execution_transition_container.ExecutionTransitionContainer;
import com.hurynovich.api_tester.service.execution_transition_container.impl.ExecutionTransitionContainerImpl;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.ValidatorTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static com.hurynovich.api_tester.model.enumeration.ValidationResultType.NON_VALID;
import static com.hurynovich.api_tester.model.enumeration.ValidationResultType.VALID;

@ExtendWith(MockitoExtension.class)
public class ExecutorExecutionSignalValidatorTest {

    private static final ExecutionTransitionContainer EXECUTION_TRANSITION_CONTAINER =
            new ExecutionTransitionContainerImpl();

    @Mock
    private Validator<GenericExecutionCacheKey> keyValidator;

    @Mock
    private ExecutionHelper executionHelper;

    @Mock
    private ExecutionState executionState;

    private Validator<ExecutionSignal> signalValidator;

    @BeforeEach
    public void init() {
        signalValidator = new ExecutorExecutionSignalValidator(keyValidator, executionHelper);
    }

    @Test
    public void validSignalTest() {
        final ExecutionSignalType executionSignalType =
                RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(executionSignalType);

        final ValidationResult keyValidationResult = ValidatorTestHelper.buildValidValidationResult();
        Mockito.when(keyValidator.validate(signal.getKey())).thenReturn(keyValidationResult);

        Mockito.when(executionHelper.getExecutionState(signal.getKey())).thenReturn(executionState);
        Mockito.when(executionHelper.resolveValidSignalNamesOnExecution(executionState)).
                thenReturn(EXECUTION_TRANSITION_CONTAINER.
                        getValidSignalTypesForState(getStateTypeByValidSignalType(executionSignalType)));

        final ValidationResult validationResult = signalValidator.validate(signal);
        Assertions.assertEquals(VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertTrue(descriptions.isEmpty());
    }

    @Test
    public void nonValidSignalTest() {
        final ExecutionSignalType executionSignalType =
                RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(executionSignalType);

        final ValidationResult keyValidationResult = ValidatorTestHelper.buildValidValidationResult();
        Mockito.when(keyValidator.validate(signal.getKey())).thenReturn(keyValidationResult);

        Mockito.when(executionHelper.getExecutionState(signal.getKey())).thenReturn(executionState);
        Mockito.when(executionHelper.resolveValidSignalNamesOnExecution(executionState)).
                thenReturn(EXECUTION_TRANSITION_CONTAINER.
                        getValidSignalTypesForState(getStateTypeByNonValidSignalType(executionSignalType)));

        final ValidationResult validationResult = signalValidator.validate(signal);
        Assertions.assertEquals(NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("Signal '" + signal.getType() + "' is not valid for state '" + executionState + "'",
                descriptions.get(0));
    }

    private ExecutionStateType getStateTypeByValidSignalType(final ExecutionSignalType signalType) {
        return Stream.of(ExecutionStateType.values()).
                filter(executionStateType ->
                        EXECUTION_TRANSITION_CONTAINER.getValidSignalTypesForState(executionStateType).
                                contains(signalType)).
                findAny().orElse(null);
    }

    private ExecutionStateType getStateTypeByNonValidSignalType(final ExecutionSignalType signalType) {
        return Stream.of(ExecutionStateType.values()).
                filter(executionStateType ->
                        !EXECUTION_TRANSITION_CONTAINER.getValidSignalTypesForState(executionStateType).
                                contains(signalType)).
                findAny().orElse(null);
    }

}
