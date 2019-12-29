package com.hurynovich.api_tester.validator.execution_signal_validator.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.execution_transition_container.ExecutionTransitionContainer;
import com.hurynovich.api_tester.service.execution_transition_container.impl.ExecutionTransitionContainerImpl;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
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

import static com.hurynovich.api_tester.model.enumeration.ExecutionStateType.PENDING_RUNNING;
import static com.hurynovich.api_tester.model.enumeration.ValidationResultType.NON_VALID;
import static com.hurynovich.api_tester.model.enumeration.ValidationResultType.VALID;

@ExtendWith(MockitoExtension.class)
public class ExecutorExecutionSignalValidatorTest {

    @Mock
    private Cache<ExecutionStateCacheKey, ExecutionState> EXECUTION_STATE_CACHE;

    @Mock
    private DTOService<UserDTO, Long> USER_SERVICE;

    @Mock
    private DTOService<RequestChainDTO, Long> REQUEST_CHAIN_SERVICE;

    @Mock
    private ExecutionState EXECUTION_STATE;

    private ExecutionTransitionContainer EXECUTION_TRANSITION_CONTAINER = new ExecutionTransitionContainerImpl();

    private Validator<ExecutionSignal> SIGNAL_VALIDATOR;

    @BeforeEach
    public void init() {
        SIGNAL_VALIDATOR = new ExecutorExecutionSignalValidator(EXECUTION_STATE_CACHE, USER_SERVICE,
                REQUEST_CHAIN_SERVICE, EXECUTION_TRANSITION_CONTAINER);
    }

    @Test
    public void executionStateNullValidationTest() {
        final ExecutionSignalType executionSignalType =
                RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);

        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(executionSignalType);

        Mockito.when(USER_SERVICE.existsById(signal.getKey().getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getKey().getRequestChainId())).thenReturn(true);

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(null);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals("State can't be null at execution stage", descriptions.get(0));
    }

    @Test
    public void executionStateTypeIsNotPendingValidationTest() {
        final ExecutionSignalType executionSignalType =
                RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);

        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(executionSignalType);

        Mockito.when(USER_SERVICE.existsById(signal.getKey().getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getKey().getRequestChainId())).thenReturn(true);

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(getNotPendingStateType());

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("Only pending states are valid at execution stage", descriptions.get(0));
    }

    @Test
    public void validSignalValidationTest() {
        final ExecutionSignalType executionSignalType =
                RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);

        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(executionSignalType);

        Mockito.when(USER_SERVICE.existsById(signal.getKey().getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getKey().getRequestChainId())).thenReturn(true);

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType stateType = getStateTypeByValidSignalType(signal.getType());
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(stateType);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertTrue(descriptions.isEmpty());
    }

    @Test
    public void nonValidSignalValidationTest() {
        final ExecutionSignalType executionSignalType =
                RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);

        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(executionSignalType);

        Mockito.when(USER_SERVICE.existsById(signal.getKey().getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getKey().getRequestChainId())).thenReturn(true);

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionSignalType signalType = signal.getType();
        final ExecutionStateType stateType = getStateTypeByNonValidSignalType(signalType);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(stateType);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals("Signal '" + signalType + "' is not valid for state '" + stateType + "'", descriptions.get(0));
    }

    private ExecutionStateType getNotPendingStateType() {
        return Stream.of(ExecutionStateType.values()).filter(executionStateType -> !executionStateType.isPendingState()).findAny().
                orElse(PENDING_RUNNING);
    }

    private ExecutionStateType getStateTypeByValidSignalType(final ExecutionSignalType signalType) {
        return Stream.of(ExecutionStateType.values()).
                filter(executionStateType ->
                        executionStateType.isPendingState() &&
                                EXECUTION_TRANSITION_CONTAINER.getValidSignalTypesForState(executionStateType).contains(signalType)
                ).
                findAny().orElse(PENDING_RUNNING);
    }

    private ExecutionStateType getStateTypeByNonValidSignalType(final ExecutionSignalType signalType) {
        return Stream.of(ExecutionStateType.values()).
                filter(executionStateType ->
                        executionStateType.isPendingState() &&
                                !EXECUTION_TRANSITION_CONTAINER.getValidSignalTypesForState(executionStateType).contains(signalType)
                ).
                findAny().orElse(PENDING_RUNNING);
    }

}
