package com.hurynovich.api_tester.controller.dto_controller.impl;

import com.hurynovich.api_tester.controller.dto_controller.GenericDTOController;
import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requestContainer")
public class RequestContainerDTOController extends GenericDTOController<RequestContainerDTO, String> {

    public RequestContainerDTOController(final @NonNull Validator<RequestContainerDTO> validator,
                                         final @NonNull DTOService<RequestContainerDTO, String> service) {
        super(validator, service);
    }

}
