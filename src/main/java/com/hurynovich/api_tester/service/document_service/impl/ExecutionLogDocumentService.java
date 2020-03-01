package com.hurynovich.api_tester.service.document_service.impl;

import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.service.document_service.GenericDocumentService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ExecutionLogDocumentService extends GenericDocumentService<ExecutionLogDocument, String> {

    public ExecutionLogDocumentService(final @NonNull MongoRepository<ExecutionLogDocument, String> repository) {
        super(repository);
    }

}
