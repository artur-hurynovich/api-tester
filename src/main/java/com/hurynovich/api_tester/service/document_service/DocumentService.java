package com.hurynovich.api_tester.service.document_service;

import java.util.List;

public interface DocumentService<D, I> {

    D create(D d);

    D readById(I id);

    List<D> readAll();

    D update(D d);

    void deleteById(I id);

}
