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

    private final Validator<D> requestValidator;

    private final DTOService<D, I> requestService;

    public GenericDTOController(final Validator<D> requestValidator, final DTOService<D, I> requestService) {
        this.requestValidator = requestValidator;
        this.requestService = requestService;
    }

    @PostMapping("/create")
    public ResponseEntity<GenericControllerResponse<D>> create(final @NonNull @RequestBody D d) {
        final ValidationResult validationResult = requestValidator.validate(d);

        final GenericControllerResponse<D> response = new GenericControllerResponse<>();
        response.setValidationResult(validationResult);

        if (validationResult.getType() == ValidationResultType.VALID) {
            response.setPayload(requestService.create(d));

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/readById")
    public ResponseEntity<GenericControllerResponse<D>> readById(final @NonNull @RequestParam I id) {
        final GenericControllerResponse<D> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        response.setPayload(requestService.readById(id));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/readAll")
    public ResponseEntity<GenericControllerResponse<List<D>>> readAll() {
        final GenericControllerResponse<List<D>> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        response.setPayload(requestService.readAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/update")
    public ResponseEntity<GenericControllerResponse<D>> update(final @NonNull @RequestBody D d) {
        return create(d);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<GenericControllerResponse<D>> deleteRequestById(final @NonNull @RequestParam I id) {
        final GenericControllerResponse<D> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        requestService.deleteById(id);

        return ResponseEntity.ok(response);
    }

}
