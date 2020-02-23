package com.hurynovich.api_tester.controller.dto_controller.impl;

import com.hurynovich.api_tester.controller.dto_controller.GenericDTOController;
import com.hurynovich.api_tester.controller.exception.ControllerException;
import com.hurynovich.api_tester.model.controller_response.impl.GenericControllerResponse;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestDTOController extends GenericDTOController<RequestDTO, Long> {

    public RequestDTOController(final @NonNull Validator<RequestDTO> requestValidator,
                                final @NonNull DTOService<RequestDTO, Long> requestService) {
        super(requestValidator, requestService);
    }

    @Override
    public ResponseEntity<GenericControllerResponse<List<RequestDTO>>> readAll() {
        throw new ControllerException("Method 'readAll' is not applicable for RequestDTO");
    }

}
