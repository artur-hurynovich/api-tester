package com.hurynovich.api_tester.controller.dto_controller.impl;

import com.hurynovich.api_tester.controller.dto_controller.GenericDTOController;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requestChain")
public class RequestChainDTOController extends GenericDTOController<RequestChainDTO, Long> {

    public RequestChainDTOController(final @NonNull Validator<RequestChainDTO> requestChainValidator,
                                     final @NonNull DTOService<RequestChainDTO, Long> requestChainService) {
        super(requestChainValidator, requestChainService);
    }

}
