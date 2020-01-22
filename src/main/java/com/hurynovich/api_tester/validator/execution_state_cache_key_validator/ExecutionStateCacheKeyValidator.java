package com.hurynovich.api_tester.validator.execution_state_cache_key_validator;

import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExecutionStateCacheKeyValidator implements Validator<GenericExecutionCacheKey> {

    private final DTOService<UserDTO, Long> userService;

    private final DTOService<RequestChainDTO, Long> requestChainService;

    public ExecutionStateCacheKeyValidator(final @NonNull DTOService<UserDTO, Long> userService,
                                           final @NonNull DTOService<RequestChainDTO, Long> requestChainService) {
        this.userService = userService;
        this.requestChainService = requestChainService;
    }

    @Override
    public ValidationResult validate(final @NonNull GenericExecutionCacheKey key) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        validateUserId(key, validationResult);

        validateRequestChainId(key, validationResult);

        return validationResult;
    }

    private void validateUserId(final @NonNull GenericExecutionCacheKey key,
                                final @NonNull ValidationResult validationResult) {
        final Long userId = key.getUserId();

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

    private void validateRequestChainId(final @NonNull GenericExecutionCacheKey key,
                                        final @NonNull ValidationResult validationResult) {
        final Long requestChainId = key.getRequestChainId();

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
