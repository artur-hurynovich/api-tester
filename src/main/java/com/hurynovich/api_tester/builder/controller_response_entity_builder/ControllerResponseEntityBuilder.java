package com.hurynovich.api_tester.builder.controller_response_entity_builder;

import com.hurynovich.api_tester.model.validation.ValidationResult;
import org.springframework.http.ResponseEntity;

public interface ControllerResponseEntityBuilder<T> {

	ResponseEntity<T> buildOk();

	ResponseEntity<T> buildValidationError(ValidationResult validationResult);

}
