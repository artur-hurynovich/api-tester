package com.hurynovich.api_tester.vlidator.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.enumeration.RequestExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.RequestExecutionStatus;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.RequestExecutionSignal;
import com.hurynovich.api_tester.model.execution.RequestExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.utils.request_execution_status.RequestExecutionSignalTypeUtils;
import com.hurynovich.api_tester.validator.Validator;
import com.hurynovich.api_tester.validator.impl.RequestExecutionSignalValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RequestExecutionSignalValidatorTest {

	private static Validator<RequestExecutionSignal> signalValidator;

	private static Cache<UUID, RequestExecutionState> requestExecutionStateCache = Mockito.mock(Cache.class);

	private static DTOService<RequestChainDTO, Long> requestChainService = Mockito.mock(DTOService.class);

	private static RequestExecutionState requestExecutionState = Mockito.mock(RequestExecutionState.class);

	@BeforeAll
	static void init() {
		signalValidator = new RequestExecutionSignalValidator(requestExecutionStateCache, requestChainService);
	}

	@Test
	public void successValidationOnStartTest() {
		final RequestExecutionSignal signal = buildValidSignalOfType(RequestExecutionSignalType.START);

		Mockito.when(requestChainService.existsById(signal.getRequestChainId())).thenReturn(true);

		final ValidationResult validationResult = signalValidator.validate(signal);
		Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
		Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
	}

	@Test
	public void successValidationTest() {
		final RequestExecutionSignal signal = buildValidSignalOfType(
			RandomValueGenerator.generateRandomEnumValueExcluding(RequestExecutionSignalType.class,
																  Collections.singletonList(
																	  RequestExecutionSignalType.START)));
		Mockito.when(requestExecutionStateCache.get(signal.getRequestExecutionStateId()))
			.thenReturn(requestExecutionState);
		final RequestExecutionStatus validRequestExecutionStatus =
			getValidRequestExecutionStatusByRequestExecutionSignal(signal);
		Mockito.when(requestExecutionState.getStatus()).thenReturn(validRequestExecutionStatus);
		Mockito.when(requestChainService.existsById(signal.getRequestChainId())).thenReturn(true);

		final ValidationResult validationResult = signalValidator.validate(signal);
		Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
		Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
	}

	@Test
	public void signalTypeNullFailureValidationTest() {
		final RequestExecutionSignal signal = buildValidSignalOfType(null);

		final RequestExecutionStatus validRequestExecutionStatus =
			getValidRequestExecutionStatusByRequestExecutionSignal(signal);
		Mockito.when(requestExecutionState.getStatus()).thenReturn(validRequestExecutionStatus);
		Mockito.when(requestChainService.existsById(signal.getRequestChainId())).thenReturn(true);

		final ValidationResult validationResult = signalValidator.validate(signal);
		Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

		final List<String> descriptions = validationResult.getDescriptions();
		Assertions.assertEquals(1, descriptions.size());
		Assertions.assertEquals("'type' can't be null", descriptions.get(0));
	}

	@Test
	public void notValidSignalTypeFailureValidationTest() {
		final RequestExecutionSignal signal = buildValidSignal();

		Mockito.when(requestExecutionStateCache.get(signal.getRequestExecutionStateId()))
			.thenReturn(requestExecutionState);
		final RequestExecutionStatus notValidRequestExecutionStatus =
			getNotValidRequestExecutionStatusByRequestExecutionSignal(signal);
		Mockito.when(requestExecutionState.getStatus()).thenReturn(notValidRequestExecutionStatus);
		Mockito.when(requestChainService.existsById(signal.getRequestChainId())).thenReturn(true);

		final ValidationResult validationResult = signalValidator.validate(signal);
		Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

		final List<String> descriptions = validationResult.getDescriptions();
		Assertions.assertEquals(1, descriptions.size());
		Assertions.assertEquals(
			"signalType '" + signal.getType() + "' is not valid for requestExecutionStatus '" + notValidRequestExecutionStatus + "'",
			descriptions.get(0));
	}

	@Test
	public void requestExecutionStateNullFailureValidationTest() {
		final RequestExecutionSignal signal = buildValidSignalOfType(
			RandomValueGenerator.generateRandomEnumValueExcluding(RequestExecutionSignalType.class,
																  Collections.singletonList(
																	  RequestExecutionSignalType.START)));

		final RequestExecutionStatus notValidRequestExecutionStatus =
			getNotValidRequestExecutionStatusByRequestExecutionSignal(signal);
		Mockito.when(requestExecutionState.getStatus()).thenReturn(notValidRequestExecutionStatus);
		Mockito.when(requestChainService.existsById(signal.getRequestChainId())).thenReturn(true);

		final ValidationResult validationResult = signalValidator.validate(signal);
		Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

		final List<String> descriptions = validationResult.getDescriptions();
		Assertions.assertEquals(1, descriptions.size());
		Assertions.assertEquals(
			"signalType '" + signal.getType() + "' can't be applied before request execution started",
			descriptions.get(0));
	}

	@Test
	public void requestChainIdNullFailureValidationTest() {
		final RequestExecutionSignal signal = buildValidSignal();
		signal.setRequestChainId(null);

		Mockito.when(requestExecutionStateCache.get(signal.getRequestExecutionStateId()))
			.thenReturn(requestExecutionState);
		final RequestExecutionStatus validRequestExecutionStatus =
			getValidRequestExecutionStatusByRequestExecutionSignal(signal);
		Mockito.when(requestExecutionState.getStatus()).thenReturn(validRequestExecutionStatus);
		Mockito.when(requestChainService.existsById(signal.getRequestChainId())).thenReturn(true);

		final ValidationResult validationResult = signalValidator.validate(signal);
		Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

		final List<String> descriptions = validationResult.getDescriptions();
		Assertions.assertEquals(1, descriptions.size());
		Assertions.assertEquals("'requestChainId' can't be null", descriptions.get(0));
	}

	@Test
	public void requestChainIdNegativeFailureValidationTest() {
		final RequestExecutionSignal signal = buildValidSignal();
		signal.setRequestChainId((long) RandomValueGenerator.generateRandomNegativeOrZeroInt());

		Mockito.when(requestExecutionStateCache.get(signal.getRequestExecutionStateId()))
			.thenReturn(requestExecutionState);
		final RequestExecutionStatus validRequestExecutionStatus =
			getValidRequestExecutionStatusByRequestExecutionSignal(signal);
		Mockito.when(requestExecutionState.getStatus()).thenReturn(validRequestExecutionStatus);
		Mockito.when(requestChainService.existsById(signal.getRequestChainId())).thenReturn(true);

		final ValidationResult validationResult = signalValidator.validate(signal);
		Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

		final List<String> descriptions = validationResult.getDescriptions();
		Assertions.assertEquals(1, descriptions.size());
		Assertions.assertEquals("'requestChainId' can't be negative or zero", descriptions.get(0));
	}

	@Test
	public void requestChainIdNonExistentFailureValidationTest() {
		final RequestExecutionSignal signal = buildValidSignal();
		signal.setRequestChainId((long) RandomValueGenerator.generateRandomPositiveInt());

		Mockito.when(requestExecutionStateCache.get(signal.getRequestExecutionStateId()))
			.thenReturn(requestExecutionState);
		final RequestExecutionStatus validRequestExecutionStatus =
			getValidRequestExecutionStatusByRequestExecutionSignal(signal);
		Mockito.when(requestExecutionState.getStatus()).thenReturn(validRequestExecutionStatus);
		Mockito.when(requestChainService.existsById(signal.getRequestChainId())).thenReturn(false);

		final ValidationResult validationResult = signalValidator.validate(signal);
		Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

		final List<String> descriptions = validationResult.getDescriptions();
		Assertions.assertEquals(1, descriptions.size());
		Assertions.assertEquals("No RequestChainDTO found for requestChainId = " + signal.getRequestChainId(),
								descriptions.get(0));
	}

	private RequestExecutionSignal buildValidSignal() {
		final RequestExecutionSignalType signalType =
			RandomValueGenerator.generateRandomEnumValue(RequestExecutionSignalType.class);
		final UUID requestExecutionStateId = RandomValueGenerator.generateRandomUUID();
		final long requestChainId = RandomValueGenerator.generateRandomPositiveInt();

		return buildSignal(signalType, requestExecutionStateId, requestChainId);
	}

	private RequestExecutionSignal buildValidSignalOfType(final RequestExecutionSignalType type) {
		final UUID requestExecutionStateId = RandomValueGenerator.generateRandomUUID();
		final long requestChainId = RandomValueGenerator.generateRandomPositiveInt();

		return buildSignal(type, requestExecutionStateId, requestChainId);
	}

	private RequestExecutionSignal buildSignal(final RequestExecutionSignalType type,
											   final UUID requestExecutionStateId,
											   final Long requestChainId) {
		final RequestExecutionSignal signal = new RequestExecutionSignal();

		signal.setType(type);
		signal.setRequestExecutionStateId(requestExecutionStateId);
		signal.setRequestChainId(requestChainId);

		return signal;
	}

	private RequestExecutionStatus getValidRequestExecutionStatusByRequestExecutionSignal(final RequestExecutionSignal signal) {
		final List<RequestExecutionStatus> requestExecutionStatuses = new ArrayList<>(Arrays.asList(RequestExecutionStatus.values()));

		requestExecutionStatuses
			.removeIf(status -> !RequestExecutionSignalTypeUtils.getValidSignalTypes(status).contains(signal.getType()));

		return requestExecutionStatuses.stream().findAny().orElse(null);
	}

	private RequestExecutionStatus getNotValidRequestExecutionStatusByRequestExecutionSignal(final RequestExecutionSignal signal) {
		final List<RequestExecutionStatus> requestExecutionStatuses = new ArrayList<>(Arrays.asList(RequestExecutionStatus.values()));

		requestExecutionStatuses
			.removeIf(status -> RequestExecutionSignalTypeUtils.getValidSignalTypes(status).contains(signal.getType()));

		return requestExecutionStatuses.stream().findAny().orElse(null);
	}

}
