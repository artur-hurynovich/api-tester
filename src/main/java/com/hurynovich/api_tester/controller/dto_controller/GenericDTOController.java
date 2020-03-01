package com.hurynovich.api_tester.controller.dto_controller;

import com.hurynovich.api_tester.model.controller_response.impl.GenericControllerResponse;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class GenericDTOController<D extends AbstractDTO, I> {

    private final Validator<D> validator;

    private final DTOService<D, I> service;

    public GenericDTOController(final Validator<D> validator, final DTOService<D, I> service) {
        this.validator = validator;
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<GenericControllerResponse<D>> create(final @NonNull @RequestBody D d) {
        final ValidationResult validationResult = validator.validate(d);

        final GenericControllerResponse<D> response = new GenericControllerResponse<>();
        response.setValidationResult(validationResult);

        if (validationResult.getType() == ValidationResultType.VALID) {
            response.setPayload(service.create(d));

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/readById")
    public ResponseEntity<GenericControllerResponse<D>> readById(final @NonNull @RequestParam I id) {
        final GenericControllerResponse<D> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        response.setPayload(service.readById(id));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/readAll")
    public ResponseEntity<GenericControllerResponse<List<D>>> readAll() {
        final GenericControllerResponse<List<D>> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        response.setPayload(service.readAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/update")
    public ResponseEntity<GenericControllerResponse<D>> update(final @NonNull @RequestBody D d) {
        final ValidationResult validationResult = validator.validate(d);

        final GenericControllerResponse<D> response = new GenericControllerResponse<>();
        response.setValidationResult(validationResult);

        if (validationResult.getType() == ValidationResultType.VALID) {
            response.setPayload(service.update(d));

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/deleteById")
    public ResponseEntity<GenericControllerResponse<D>> deleteRequestById(final @NonNull @RequestParam I id) {
        final GenericControllerResponse<D> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        service.deleteById(id);

        return ResponseEntity.ok(response);
    }

}
