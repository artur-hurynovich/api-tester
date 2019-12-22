package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.enumeration.RequestExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.RequestExecutionSignal;
import com.hurynovich.api_tester.model.execution.RequestExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.utils.request_execution_status.RequestExecutionSignalTypeUtils;
import com.hurynovich.api_tester.validator.Validator;

import java.util.ArrayList;
import java.util.UUID;

public class RequestExecutionSignalValidator implements Validator<RequestExecutionSignal> {

	private final Cache<UUID, RequestExecutionState> requestExecutionStateCache;

	private final DTOService<RequestChainDTO, Long> requestChainService;

	public RequestExecutionSignalValidator(final Cache<UUID, RequestExecutionState> requestExecutionStateCache,
										   final DTOService<RequestChainDTO, Long> requestChainService) {
		this.requestExecutionStateCache = requestExecutionStateCache;
		this.requestChainService = requestChainService;
	}

	@Override
	public ValidationResult validate(final RequestExecutionSignal signal) {
		final ValidationResult validationResult = new ValidationResult();
		validationResult.setType(ValidationResultType.VALID);
		validationResult.setDescriptions(new ArrayList<>());

		validateType(signal, validationResult);
		validateRequestChainId(signal, validationResult);

		return validationResult;
	}

	private void validateType(final RequestExecutionSignal signal, final ValidationResult validationResult) {
		final RequestExecutionSignalType type = signal.getType();

		if (type == null) {
			validationResult.setType(ValidationResultType.NON_VALID);
			validationResult.getDescriptions().add("'type' can't be null");
		} else {
			processNotNullSignalTypeValidation(signal, validationResult);
		}
	}

	private void processNotNullSignalTypeValidation(final RequestExecutionSignal signal,
													final ValidationResult validationResult) {
		final UUID requestExecutionStateId = signal.getRequestExecutionStateId();
		final RequestExecutionState requestExecutionState;
		if (requestExecutionStateId == null) {
			requestExecutionState = null;
		} else {
			requestExecutionState = requestExecutionStateCache.get(requestExecutionStateId);
		}

		final RequestExecutionSignalType signalType = signal.getType();
		switch (signalType) {
			case START:
				if (requestExecutionState != null && nonValidSignalType(signal, requestExecutionState)) {
					validationResult.setType(ValidationResultType.NON_VALID);
					validationResult.getDescriptions().add(
						"signalType '" + signalType + "' is not valid for requestExecutionStatus '" +
						requestExecutionState.getStatus() + "'");
				}
				break;
			case PAUSE: case RESUME: case STOP:
				if (requestExecutionState == null) {
					validationResult.setType(ValidationResultType.NON_VALID);
					validationResult.getDescriptions().add(
						"signalType '" + signalType + "' can't be applied before request execution started");
				} else if (nonValidSignalType(signal, requestExecutionState)) {
					validationResult.setType(ValidationResultType.NON_VALID);
					validationResult.getDescriptions().add(
						"signalType '" + signalType + "' is not valid for requestExecutionStatus '" +
						requestExecutionState.getStatus() + "'");
				}
				break;
			default:
				validationResult.setType(ValidationResultType.NON_VALID);
				validationResult.getDescriptions().add("Unknown signal signalType: '" + signalType + "'");
		}
	}

	private boolean nonValidSignalType(final RequestExecutionSignal signal,
									   final RequestExecutionState requestExecutionState) {
		return !RequestExecutionSignalTypeUtils.getValidSignalTypes(requestExecutionState.getStatus())
			.contains(signal.getType());
	}

	private void validateRequestChainId(final RequestExecutionSignal signal, final ValidationResult validationResult) {
		final Long requestChainId = signal.getRequestChainId();

		if (requestChainId == null) {
			validationResult.setType(ValidationResultType.NON_VALID);
			validationResult.getDescriptions().add("'requestChainId' can't be null");
		} else if (requestChainId <= 0) {
			validationResult.setType(ValidationResultType.NON_VALID);
			validationResult.getDescriptions().add("'requestChainId' can't be negative or zero");
		} else if (!requestChainService.existsById(requestChainId)) {
			validationResult.setType(ValidationResultType.NON_VALID);
			validationResult.getDescriptions().add("No RequestChainDTO found for requestChainId = " + requestChainId);
		}

	}

}
