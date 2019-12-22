package com.hurynovich.api_tester.builder.controller_response_entity_builder.impl;

import com.hurynovich.api_tester.builder.controller_response_entity_builder.ControllerResponseEntityBuilder;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import org.springframework.http.ResponseEntity;

public class ControllerResponseEntityBuilderImpl implements ControllerResponseEntityBuilder<String> {

	@Override
	public ResponseEntity<String> buildOk() {
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<String> buildValidationError(final ValidationResult validationResult) {
		return ResponseEntity.badRequest().body(validationResult.getDescriptionsAsString());
	}

}
