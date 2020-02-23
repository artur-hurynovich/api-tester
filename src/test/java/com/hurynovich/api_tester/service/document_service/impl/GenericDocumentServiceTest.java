package com.hurynovich.api_tester.service.document_service.impl;

import com.hurynovich.api_tester.model.document.AbstractDocument;
import com.hurynovich.api_tester.service.document_service.DocumentService;
import com.hurynovich.api_tester.test_helper.DocumentServiceTestHelper;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class GenericDocumentServiceTest<D extends AbstractDocument> {

    protected static final int DEFAULT_DTO_COUNT = 5;

    private final Supplier<List<D>> documentsSupplier;

    private final DocumentServiceTestHelper<D> helper;

    public GenericDocumentServiceTest(final Supplier<List<D>> documentsSupplier,
                                      final Supplier<MongoRepository<D, String>> repositorySupplier,
                                      final Function<MongoRepository<D, String>, DocumentService<D, String>> serviceFunction,
                                      final BiConsumer<D, D> checkConsumer) {
        this.documentsSupplier = documentsSupplier;

        final MongoRepository<D, String> repository = repositorySupplier.get();

        helper = new DocumentServiceTestHelper<>(repository, serviceFunction.apply(repository), checkConsumer);
    }

    public void createTest() {
        final D document = documentsSupplier.get().iterator().next();

        helper.processCreateTest(document);
    }

    public void readByIdSuccessTest() {
        final D document = documentsSupplier.get().iterator().next();

        helper.processReadByIdSuccessTest(document);
    }

    public void readByIdFailureTest() {
        helper.processReadByIdFailureTest();
    }

    public void updateTest(final Function<D, D> updateFunction) {
        final D document = documentsSupplier.get().iterator().next();

        helper.processUpdateTest(document, updateFunction);
    }

    public void deleteByIdSuccessTest() {
        final D document = documentsSupplier.get().iterator().next();

        helper.processDeleteByIdSuccessTest(document);
    }

    public void deleteByIdFailureTest() {
        helper.processDeleteByIdFailureTest();
    }

}
