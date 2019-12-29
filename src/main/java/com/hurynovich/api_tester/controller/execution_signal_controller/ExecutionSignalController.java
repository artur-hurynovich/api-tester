package com.hurynovich.api_tester.controller.execution_signal_controller;

import com.hurynovich.api_tester.model.controller_response.impl.ExecutionSignalControllerResponse;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.validator.Validator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionSignalController {

    private final Validator<ExecutionSignal> signalValidator;

    private final ExecutionHelper executionHelper;

    public ExecutionSignalController(final @NonNull @Qualifier("controllerExecutionSignalValidator") Validator<ExecutionSignal> signalValidator,
                                     final @NonNull ExecutionHelper executionHelper) {
        this.signalValidator = signalValidator;
        this.executionHelper = executionHelper;
    }

    @PostMapping("/signal")
    public ResponseEntity<ExecutionSignalControllerResponse> postSignal(final @NonNull @RequestBody ExecutionSignal executionSignal) {
        final ValidationResult validationResult = signalValidator.validate(executionSignal);

        final ExecutionSignalControllerResponse response = new ExecutionSignalControllerResponse();
        if (validationResult.getType() == ValidationResultType.VALID) {
            final ExecutionState executionState = executionHelper.updateExecutionStateCache(executionSignal);
            response.setPayload(executionState);
            response.setValidationResult(validationResult);

            // TODO send signal to Kafka

            return ResponseEntity.ok(response);
        } else {
            response.setValidationResult(validationResult);

            return ResponseEntity.badRequest().body(response);
        }
    }

}
