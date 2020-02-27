package com.hurynovich.api_tester.controller.dto_controller.impl;

import com.hurynovich.api_tester.controller.dto_controller.GenericDTOController;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestDTOController extends GenericDTOController<RequestDTO, Long> {

    public RequestDTOController(final @NonNull Validator<RequestDTO> requestValidator,
                                final @NonNull DTOService<RequestDTO, Long> requestService) {
        super(requestValidator, requestService);
    }

}
