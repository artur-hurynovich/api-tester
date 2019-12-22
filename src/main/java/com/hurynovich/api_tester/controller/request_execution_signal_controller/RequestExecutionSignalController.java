package com.hurynovich.api_tester.controller.request_execution_signal_controller;

import com.hurynovich.api_tester.builder.controller_response_entity_builder.ControllerResponseEntityBuilder;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.RequestExecutionSignal;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestExecutionSignalController {

	private final Validator<RequestExecutionSignal> signalValidator;

	private final ControllerResponseEntityBuilder<String> responseEntityBuilder;

	public RequestExecutionSignalController(final Validator<RequestExecutionSignal> signalValidator,
											final ControllerResponseEntityBuilder<String> responseEntityBuilder) {
		this.signalValidator = signalValidator;
		this.responseEntityBuilder = responseEntityBuilder;
	}

	@PostMapping("/signal")
	public ResponseEntity<String> postSignal(final @RequestBody RequestExecutionSignal signal) {
		final ValidationResult validationResult = signalValidator.validate(signal);

		if (validationResult.getType() == ValidationResultType.VALID) {
			return responseEntityBuilder.buildOk();
		} else {
			return responseEntityBuilder.buildValidationError(validationResult);
		}
	}

}
