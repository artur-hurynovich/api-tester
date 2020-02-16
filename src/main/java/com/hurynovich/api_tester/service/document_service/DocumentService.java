package com.hurynovich.api_tester.service.document_service;

import com.hurynovich.api_tester.model.document.MongoDBDocument;

public interface DocumentService<D extends MongoDBDocument, I> {

    D create(D d);

    D readById(I id);

    D update(D d);

    void deleteById(I id);

}
