package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.service.execution_helper.impl.ExecutionHelperImpl;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.validator.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutionSignalValidatorTest {

    private static final Cache<ExecutionStateCacheKey, ExecutionState> EXECUTION_STATE_CACHE = Mockito.mock(Cache.class);

    private static final DTOService<UserDTO, Long> USER_SERVICE = Mockito.mock(DTOService.class);
    private static final DTOService<RequestChainDTO, Long> REQUEST_CHAIN_SERVICE = Mockito.mock(DTOService.class);

    private static final ExecutionState EXECUTION_STATE = Mockito.mock(ExecutionState.class);

    private static final ExecutionHelper EXECUTION_HELPER = new ExecutionHelperImpl(EXECUTION_STATE_CACHE);

    private static final Validator<ExecutionSignal> SIGNAL_VALIDATOR =
            new ExecutionSignalValidator(EXECUTION_STATE_CACHE, USER_SERVICE, REQUEST_CHAIN_SERVICE, EXECUTION_HELPER);

    @Test
    public void successValidationOnStartTest() {
        final ExecutionSignal signal = buildValidSignalOfType(ExecutionSignalType.RUN);

        Mockito.when(USER_SERVICE.existsById(signal.getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void successValidationTest() {
        final ExecutionSignal signal = buildValidSignalOfType(
                RandomValueGenerator.generateRandomEnumValueExcluding(ExecutionSignalType.class,
                        Collections.singletonList(ExecutionSignalType.RUN)));
        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType validExecutionStateType = getValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(validExecutionStateType);
        Mockito.when(USER_SERVICE.existsById(signal.getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void signalTypeNullFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignalOfType(null);

        final ExecutionStateType validExecutionStateType = getValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(validExecutionStateType);
        Mockito.when(USER_SERVICE.existsById(signal.getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'type' can't be null", descriptions.get(0));
    }

    @Test
    public void notValidSignalTypeFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignal();

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType notValidExecutionStateType = getNotValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(notValidExecutionStateType);
        Mockito.when(USER_SERVICE.existsById(signal.getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("signalType '" + signal.getType() +
                "' is not valid for requestExecutionStatus '" + notValidExecutionStateType + "'", descriptions.get(0));
    }

    @Test
    public void executionStateNullFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignalOfType(
                RandomValueGenerator.generateRandomEnumValueExcluding(ExecutionSignalType.class,
                        Collections.singletonList(ExecutionSignalType.RUN)));

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(null);

        Mockito.when(USER_SERVICE.existsById(signal.getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("signalType '" + signal.getType() +
                "' can't be applied before request execution started", descriptions.get(0));
    }

    @Test
    public void userIdNullFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignal();
        signal.setUserId(null);

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType validExecutionStateType = getValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(validExecutionStateType);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'userId' can't be null", descriptions.get(0));
    }

    @Test
    public void userIdNegativeFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignal();
        signal.setUserId((long) RandomValueGenerator.generateRandomNegativeOrZeroInt());

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType validExecutionStateType = getValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(validExecutionStateType);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'userId' can't be negative or zero", descriptions.get(0));
    }

    @Test
    public void userIdNonExistentFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignal();
        signal.setUserId((long) RandomValueGenerator.generateRandomPositiveInt());

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType validExecutionStateType = getValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(validExecutionStateType);
        Mockito.when(USER_SERVICE.existsById(signal.getRequestChainId())).thenReturn(false);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("No UserDTO found for userId = " + signal.getUserId(), descriptions.get(0));
    }

    @Test
    public void requestChainIdNullFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignal();
        signal.setRequestChainId(null);

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType validExecutionStateType = getValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(validExecutionStateType);
        Mockito.when(USER_SERVICE.existsById(signal.getUserId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestChainId' can't be null", descriptions.get(0));
    }

    @Test
    public void requestChainIdNegativeFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignal();
        signal.setRequestChainId((long) RandomValueGenerator.generateRandomNegativeOrZeroInt());

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType validExecutionStateType = getValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(validExecutionStateType);
        Mockito.when(USER_SERVICE.existsById(signal.getUserId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestChainId' can't be negative or zero", descriptions.get(0));
    }

    @Test
    public void requestChainIdNonExistentFailureValidationTest() {
        final ExecutionSignal signal = buildValidSignal();
        signal.setRequestChainId((long) RandomValueGenerator.generateRandomPositiveInt());

        Mockito.when(EXECUTION_STATE_CACHE.get(Mockito.any(ExecutionStateCacheKey.class))).thenReturn(EXECUTION_STATE);

        final ExecutionStateType validExecutionStateType = getValidExecutionStatusByExecutionSignal(signal);
        Mockito.when(EXECUTION_STATE.getType()).thenReturn(validExecutionStateType);
        Mockito.when(USER_SERVICE.existsById(signal.getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getRequestChainId())).thenReturn(false);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("No RequestChainDTO found for requestChainId = " + signal.getRequestChainId(),
                descriptions.get(0));
    }

    private ExecutionSignal buildValidSignal() {
        final ExecutionSignalType signalType = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final long userId = RandomValueGenerator.generateRandomPositiveInt();
        final long requestChainId = RandomValueGenerator.generateRandomPositiveInt();

        return buildSignal(signalType, userId, requestChainId);
    }

    private ExecutionSignal buildValidSignalOfType(final ExecutionSignalType type) {
        final long userId = RandomValueGenerator.generateRandomPositiveInt();
        final long requestChainId = RandomValueGenerator.generateRandomPositiveInt();

        return buildSignal(type, userId, requestChainId);
    }

    private ExecutionSignal buildSignal(final ExecutionSignalType type,
                                        final Long userId,
                                        final Long requestChainId) {
        final ExecutionSignal signal = new ExecutionSignal();

        signal.setType(type);
        signal.setUserId(userId);
        signal.setRequestChainId(requestChainId);

        return signal;
    }

    private ExecutionStateType getValidExecutionStatusByExecutionSignal(final ExecutionSignal signal) {
        final List<ExecutionStateType> executionStateTypes = new ArrayList<>(Arrays.asList(ExecutionStateType.values()));
        final List<ExecutionState> executionStates =
                executionStateTypes.stream().map(ExecutionTestHelper::buildExecutionState).collect(Collectors.toList());

        executionStates
                .removeIf(state -> !EXECUTION_HELPER.resolveValidExecutionSignalTypes(state).contains(signal.getType()));

        return executionStates.stream().findAny().map(ExecutionState::getType).orElse(null);
    }

    private ExecutionStateType getNotValidExecutionStatusByExecutionSignal(final ExecutionSignal signal) {
        final List<ExecutionStateType> executionStateTypes = new ArrayList<>(Arrays.asList(ExecutionStateType.values()));
        final List<ExecutionState> executionStates =
                executionStateTypes.stream().map(ExecutionTestHelper::buildExecutionState).collect(Collectors.toList());

        executionStates
                .removeIf(state -> EXECUTION_HELPER.resolveValidExecutionSignalTypes(state).contains(signal.getType()));

        return executionStates.stream().findAny().map(ExecutionState::getType).orElse(null);
    }

}
