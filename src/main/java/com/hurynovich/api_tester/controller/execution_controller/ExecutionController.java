package com.hurynovich.api_tester.controller.execution_controller;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.controller_response.impl.GenericExecutionControllerResponse;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExecutionController {

    private final Validator<ExecutionSignal> executionSignalValidator;
    private final Validator<ExecutionStateCacheKey> executionStateCacheKeyValidator;

    private final ExecutionHelper executionHelper;

    public ExecutionController(final @NonNull @Qualifier("controllerExecutionSignalValidator") Validator<ExecutionSignal> executionSignalValidator,
                               final @NonNull Validator<ExecutionStateCacheKey> executionStateCacheKeyValidator,
                               final @NonNull ExecutionHelper executionHelper) {
        this.executionSignalValidator = executionSignalValidator;
        this.executionStateCacheKeyValidator = executionStateCacheKeyValidator;
        this.executionHelper = executionHelper;
    }

    @PostMapping("/signal")
    public ResponseEntity<GenericExecutionControllerResponse> postSignal(final @NonNull @RequestBody ExecutionSignal executionSignal) {
        final ValidationResult validationResult = executionSignalValidator.validate(executionSignal);

        final GenericExecutionControllerResponse response = new GenericExecutionControllerResponse();
        response.setValidationResult(validationResult);
        if (validationResult.getType() == ValidationResultType.VALID) {
            final ExecutionState executionState = executionHelper.updateExecutionStateCache(executionSignal);
            response.setState(executionState.getType());

            final List<ExecutionSignalType> validSignalTypes =
                    executionHelper.resolveValidSignalTypesOnInit(executionState);
            response.setValidSignals(validSignalTypes);

            // TODO send signal to Kafka

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/validSignals")
    public ResponseEntity<GenericExecutionControllerResponse> getValidSignalTypes(final @NonNull @RequestBody ExecutionStateCacheKey key) {
        final ValidationResult validationResult = executionStateCacheKeyValidator.validate(key);

        final GenericExecutionControllerResponse response = new GenericExecutionControllerResponse();
        response.setValidationResult(validationResult);
        if (validationResult.getType() == ValidationResultType.VALID) {
            final ExecutionState executionState = executionHelper.getExecutionState(key);
            response.setState(executionState.getType());

            final List<ExecutionSignalType> validSignalTypes =
                    executionHelper.resolveValidSignalTypesOnInit(executionState);
            response.setValidSignals(validSignalTypes);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
