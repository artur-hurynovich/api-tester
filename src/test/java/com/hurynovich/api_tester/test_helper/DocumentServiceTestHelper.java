package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.model.document.AbstractDocument;
import com.hurynovich.api_tester.service.document_service.DocumentService;

import org.junit.jupiter.api.Assertions;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

public class DocumentServiceTestHelper<D extends AbstractDocument> {

    private final MongoRepository<D, String> repository;

    private final DocumentService<D, String> service;

    private final BiConsumer<D, D> checkConsumer;

    public DocumentServiceTestHelper(final MongoRepository<D, String> repository,
                                     final DocumentService<D, String> service,
                                     final BiConsumer<D, D> checkConsumer) {
        this.repository = repository;
        this.service = service;
        this.checkConsumer = checkConsumer;
    }

    public void processCreateTest(final D document) {
        repository.deleteAll();

        final D storedDto = service.create(document);

        checkConsumer.accept(document, storedDto);
    }

    public void processReadByIdSuccessTest(final D document) {
        repository.deleteAll();

        repository.save(document);

        service.readById(document.getId());
    }

    public void processReadAllTest(final List<D> documents) {
        repository.deleteAll();

        documents.forEach(repository::save);

        final List<D> storedDocuments = service.readAll();

        Assertions.assertEquals(documents.size(), storedDocuments.size());

        for (int i = 0; i < documents.size(); i++) {
            final D document = documents.get(i);
            final D storedDocument = storedDocuments.get(i);

            checkConsumer.accept(document, storedDocument);
        }
    }

    public void processReadByIdFailureTest() {
        final String id = RandomValueGenerator.generateRandomStringLettersOnly(5);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readById(id));
    }

    public void processUpdateTest(final D document, final Function<D, D> updateFunction) {
        repository.deleteAll();

        repository.save(document);

        final D updatedDocument = updateFunction.apply(document);

        service.update(updatedDocument);

        final D storedDocument = service.readById(document.getId());

        checkConsumer.accept(updatedDocument, storedDocument);
    }

    public void processDeleteByIdSuccessTest(final D document) {
        repository.deleteAll();

        repository.save(document);

        service.deleteById(document.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readById(document.getId()));
    }

    public void processDeleteByIdFailureTest() {
        repository.deleteAll();

        final String id = RandomValueGenerator.generateRandomStringLettersOnly(5);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(id));
    }

}
