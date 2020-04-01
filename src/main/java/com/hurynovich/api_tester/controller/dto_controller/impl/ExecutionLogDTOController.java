package com.hurynovich.api_tester.controller.dto_controller.impl;

import com.hurynovich.api_tester.controller.dto_controller.GenericDTOController;
import com.hurynovich.api_tester.model.controller_response.impl.GenericControllerResponse;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/executionLog")
public class ExecutionLogDTOController extends GenericDTOController<ExecutionLogDTO, String> {

    public ExecutionLogDTOController(final @NonNull Validator<ExecutionLogDTO> validator,
                                     final @NonNull DTOService<ExecutionLogDTO, String> service) {
        super(validator, service);
    }

    @Override
    public ResponseEntity<GenericControllerResponse<ExecutionLogDTO>> create(final @NonNull @RequestBody ExecutionLogDTO executionLogDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<GenericControllerResponse<ExecutionLogDTO>> update(final @NonNull @RequestBody ExecutionLogDTO executionLogDTO) {
        throw new UnsupportedOperationException();
    }

}
