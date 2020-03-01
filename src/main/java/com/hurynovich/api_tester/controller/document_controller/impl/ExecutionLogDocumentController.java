package com.hurynovich.api_tester.controller.document_controller.impl;

import com.hurynovich.api_tester.controller.document_controller.GenericDocumentController;
import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.service.document_service.DocumentService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/executionLog")
public class ExecutionLogDocumentController extends GenericDocumentController<ExecutionLogDocument, String> {

    public ExecutionLogDocumentController(final @NonNull Validator<ExecutionLogDocument> validator,
                                          final @NonNull DocumentService<ExecutionLogDocument, String> service) {
        super(validator, service);
    }

}
