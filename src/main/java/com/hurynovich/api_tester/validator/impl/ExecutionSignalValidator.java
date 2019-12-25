package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.validator.Validator;

import java.util.ArrayList;

public class ExecutionSignalValidator implements Validator<ExecutionSignal> {

    private final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache;

    private final DTOService<UserDTO, Long> userService;
    private final DTOService<RequestChainDTO, Long> requestChainService;

    private final ExecutionHelper executionHelper;

    public ExecutionSignalValidator(final Cache<ExecutionStateCacheKey, ExecutionState> executionStateCache,
                                    final DTOService<UserDTO, Long> userService,
                                    final DTOService<RequestChainDTO, Long> requestChainService,
                                    final ExecutionHelper executionHelper) {
        this.executionStateCache = executionStateCache;
        this.userService = userService;
        this.requestChainService = requestChainService;
        this.executionHelper = executionHelper;
    }

    @Override
    public ValidationResult validate(final ExecutionSignal signal) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        validateType(signal, validationResult);
        validateUserId(signal, validationResult);
        validateRequestChainId(signal, validationResult);

        return validationResult;
    }

    private void validateType(final ExecutionSignal signal, final ValidationResult validationResult) {
        final ExecutionSignalType type = signal.getType();

        if (type == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'type' can't be null");
        } else {
            processNotNullSignalTypeValidation(signal, validationResult);
        }
    }

    private void processNotNullSignalTypeValidation(final ExecutionSignal signal,
                                                    final ValidationResult validationResult) {
        final Long userId = signal.getUserId();
        final Long requestChainId = signal.getRequestChainId();
        final ExecutionState executionState =
                executionStateCache.get(new ExecutionStateCacheKey(userId, requestChainId));

        final ExecutionSignalType signalType = signal.getType();
        switch (signalType) {
            case RUN:
                if (executionState != null && nonValidSignalType(signal, executionState)) {
                    validationResult.setType(ValidationResultType.NON_VALID);
                    validationResult.getDescriptions().add(
                            "signalType '" + signalType + "' is not valid for requestExecutionStatus '" +
                                    executionState.getType() + "'");
                }
                break;
            case PAUSE:
            case RESUME:
            case STOP:
                if (executionState == null) {
                    validationResult.setType(ValidationResultType.NON_VALID);
                    validationResult.getDescriptions().add(
                            "signalType '" + signalType + "' can't be applied before request execution started");
                } else if (nonValidSignalType(signal, executionState)) {
                    validationResult.setType(ValidationResultType.NON_VALID);
                    validationResult.getDescriptions().add(
                            "signalType '" + signalType + "' is not valid for requestExecutionStatus '" +
                                    executionState.getType() + "'");
                }
                break;
            default:
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("Unknown signalType: '" + signalType + "'");
        }
    }

    private boolean nonValidSignalType(final ExecutionSignal signal,
                                       final ExecutionState executionState) {
        return !executionHelper.resolveValidExecutionSignalTypes(executionState).contains(signal.getType());
    }

    private void validateUserId(final ExecutionSignal signal, final ValidationResult validationResult) {
        final Long userId = signal.getUserId();

        if (userId == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'userId' can't be null");
        } else if (userId <= 0) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'userId' can't be negative or zero");
        } else if (!userService.existsById(userId)) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("No UserDTO found for userId = " + userId);
        }
    }

    private void validateRequestChainId(final ExecutionSignal signal, final ValidationResult validationResult) {
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
