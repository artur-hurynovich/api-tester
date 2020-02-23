package com.hurynovich.api_tester.service.document_service;

public interface DocumentService<D, I> {

    D create(D d);

    D readById(I id);

    D update(D d);

    void deleteById(I id);

}
