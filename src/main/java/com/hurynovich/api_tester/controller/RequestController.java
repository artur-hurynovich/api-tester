package com.hurynovich.api_tester.controller;

import com.hurynovich.api_tester.model.controller_response.impl.GenericControllerResponse;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final Validator<RequestDTO> requestValidator;

    private final DTOService<RequestDTO, Long> requestService;

    public RequestController(final Validator<RequestDTO> requestValidator,
                             final DTOService<RequestDTO, Long> requestService) {
        this.requestValidator = requestValidator;
        this.requestService = requestService;
    }

    @PostMapping("/create")
    public ResponseEntity<GenericControllerResponse<RequestDTO>> createRequest(final @NonNull @RequestBody RequestDTO request) {
        final ValidationResult validationResult = requestValidator.validate(request);

        final GenericControllerResponse<RequestDTO> response = new GenericControllerResponse<>();
        response.setValidationResult(validationResult);

        if (validationResult.getType() == ValidationResultType.VALID) {
            response.setPayload(requestService.create(request));

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/readById")
    public ResponseEntity<GenericControllerResponse<RequestDTO>> readRequestById(final @NonNull @RequestParam Long id) {
        final GenericControllerResponse<RequestDTO> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        response.setPayload(requestService.readById(id));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/readAll")
    public ResponseEntity<GenericControllerResponse<List<RequestDTO>>> updateRequest(final @NonNull @RequestParam Long id) {
        final GenericControllerResponse<List<RequestDTO>> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        response.setPayload(requestService.readAll());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<GenericControllerResponse<RequestDTO>> readRequestById(final @NonNull @RequestBody RequestDTO request) {
        final GenericControllerResponse<RequestDTO> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        response.setPayload(requestService.update(request));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<GenericControllerResponse<RequestDTO>> deleteRequestById(final @NonNull @RequestParam Long id) {
        final GenericControllerResponse<RequestDTO> response = new GenericControllerResponse<>();
        response.setValidationResult(ValidationResult.createValidResult());

        requestService.deleteById(id);

        return ResponseEntity.ok(response);
    }

}
