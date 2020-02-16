package com.hurynovich.api_tester.service.document_service;

import com.hurynovich.api_tester.model.document.MongoDBDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import javax.persistence.EntityNotFoundException;

public class GenericDocumentService<D extends MongoDBDocument, I> implements DocumentService<D, I> {

    private final MongoRepository<D, I> repository;

    public GenericDocumentService(final @NonNull MongoRepository<D, I> repository) {
        this.repository = repository;
    }

    @Override
    public D create(final D d) {
        return repository.save(d);
    }

    @Override
    public D readById(final I id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public D update(final D d) {
        return create(d);
    }

    @Override
    public void deleteById(final I id) {
        repository.deleteById(id);
    }

}
