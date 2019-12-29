package com.hurynovich.api_tester.validator.execution_signal_validator;

import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;

import org.springframework.lang.NonNull;

import java.util.ArrayList;

public abstract class AbstractExecutionSignalValidator implements Validator<ExecutionSignal> {

    private final DTOService<UserDTO, Long> userService;
    private final DTOService<RequestChainDTO, Long> requestChainService;

    public AbstractExecutionSignalValidator(final @NonNull DTOService<UserDTO, Long> userService,
                                            final @NonNull DTOService<RequestChainDTO, Long> requestChainService) {
        this.userService = userService;
        this.requestChainService = requestChainService;
    }

    @Override
    public ValidationResult validate(final @NonNull ExecutionSignal signal) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        validateExecutionStateCacheKey(signal, validationResult);

        validateExecutionSignalType(signal, validationResult);

        return validationResult;
    }

    private void validateExecutionStateCacheKey(final @NonNull ExecutionSignal signal,
                                                final @NonNull ValidationResult validationResult) {
        if (signal.getKey() == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'key' can't be null");
        } else {
            validateUserId(signal, validationResult);
            validateRequestChainId(signal, validationResult);
        }
    }

    private void validateUserId(final @NonNull ExecutionSignal signal,
                                final @NonNull ValidationResult validationResult) {
        final Long userId = signal.getKey().getUserId();

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

    private void validateRequestChainId(final @NonNull ExecutionSignal signal,
                                        final @NonNull ValidationResult validationResult) {
        final Long requestChainId = signal.getKey().getRequestChainId();

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

    private void validateExecutionSignalType(final @NonNull ExecutionSignal signal,
                                             final @NonNull ValidationResult validationResult) {
        final ExecutionSignalType type = signal.getType();

        if (type == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'type' can't be null");
        } else {
            processNotNullSignalTypeValidation(signal, validationResult);
        }
    }

    protected abstract void processNotNullSignalTypeValidation(final @NonNull ExecutionSignal signal,
                                                               final @NonNull ValidationResult validationResult);

}
