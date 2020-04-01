package com.hurynovich.api_tester.controller.execution_controller;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.controller_response.impl.ExecutionControllerResponse;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
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

    private final Validator<RequestContainerDTO> requestContainerValidator;

    private final Validator<ExecutionSignal> executionSignalValidator;
    private final Validator<ExecutionCacheKey> executionCacheKeyValidator;

    private final ExecutionHelper executionHelper;

    public ExecutionController(final @NonNull Validator<RequestContainerDTO> requestContainerValidator,
                               final @NonNull @Qualifier("controllerExecutionSignalValidator") Validator<ExecutionSignal> executionSignalValidator,
                               final @NonNull Validator<ExecutionCacheKey> executionCacheKeyValidator,
                               final @NonNull ExecutionHelper executionHelper) {
        this.requestContainerValidator = requestContainerValidator;
        this.executionSignalValidator = executionSignalValidator;
        this.executionCacheKeyValidator = executionCacheKeyValidator;
        this.executionHelper = executionHelper;
    }

    @GetMapping("/state")
    public ResponseEntity<ExecutionControllerResponse> getState(final @NonNull @RequestBody ExecutionCacheKey executionCacheKey) {
        final ValidationResult validationResult = executionCacheKeyValidator.validate(executionCacheKey);

        final ExecutionControllerResponse response = new ExecutionControllerResponse();
        response.setValidationResult(validationResult);

        if (validationResult.getType() == ValidationResultType.VALID) {
            final ExecutionState executionState = executionHelper.getExecutionState(executionCacheKey);

            response.setStateName(executionState.getState().getName());

            final List<String> validSignalNames = executionHelper.resolveValidSignalNamesOnInit(executionState);
            response.setValidSignalNames(validSignalNames);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/initExecution")
    public ResponseEntity<ExecutionControllerResponse> postInitExecution(final @NonNull @RequestBody RequestContainerDTO requestContainer) {
        final ValidationResult validationResult = requestContainerValidator.validate(requestContainer);

        final ExecutionControllerResponse response = new ExecutionControllerResponse();
        response.setValidationResult(validationResult);

        if (validationResult.getType() == ValidationResultType.VALID) {
            final ExecutionCacheKey executionCacheKey = executionHelper.initExecutionStateCache(requestContainer);

            response.setExecutionKey(executionCacheKey.getExecutionKey());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/signal")
    public ResponseEntity<ExecutionControllerResponse> postSignal(final @NonNull @RequestBody ExecutionSignal executionSignal) {
        final ValidationResult validationResult = executionSignalValidator.validate(executionSignal);

        final ExecutionControllerResponse response = new ExecutionControllerResponse();
        response.setValidationResult(validationResult);

        if (validationResult.getType() == ValidationResultType.VALID) {
            final ExecutionState executionState = executionHelper.updateExecutionStateCache(executionSignal);

            response.setStateName(executionState.getState().getName());

            final List<String> validSignalNames = executionHelper.resolveValidSignalNamesOnInit(executionState);
            response.setValidSignalNames(validSignalNames);

            // TODO send signal to Kafka

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
