package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.PersistentObject;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.test_helper.DTOServiceTestHelper;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class GenericDTOServiceTest<D extends AbstractDTO<I>, P extends PersistentObject<I>, I extends Serializable> {

    protected static final int DEFAULT_DTO_COUNT = 5;

    private Supplier<List<D>> dtosSupplier;

    private DTOServiceTestHelper<D, P, I> helper;

    public void init(final Supplier<List<D>> dtosSupplier,
                     final Supplier<I> idSupplier,
                     final GenericRepository<P, I> repository,
                     final DTOConverter<D, P, I> converter,
                     final DTOService<D, I> service,
                     final BiConsumer<D, D> checkConsumer) {
        this.dtosSupplier = dtosSupplier;

        helper = new DTOServiceTestHelper<>(idSupplier, repository, converter, service, checkConsumer);
    }

    public void createTest() {
        final D dto = dtosSupplier.get().iterator().next();

        helper.processCreateTest(dto);
    }

    public void readByIdSuccessTest() {
        final D dto = dtosSupplier.get().iterator().next();

        helper.processReadByIdSuccessTest(dto);
    }

    public void readByIdFailureTest() {
        helper.processReadByIdFailureTest();
    }

    public void readAllTest() {
        final List<D> dtos = dtosSupplier.get();

        helper.processReadAllTest(dtos);
    }

    public void updateTest(final Function<D, D> updateFunction) {
        final D dto = dtosSupplier.get().iterator().next();

        helper.processUpdateTest(dto, updateFunction);
    }

    public void deleteByIdSuccessTest() {
        final D dto = dtosSupplier.get().iterator().next();

        helper.processDeleteByIdSuccessTest(dto);
    }

    public void deleteByIdFailureTest() {
        helper.processDeleteByIdFailureTest();
    }

    public void existsByIdSuccessTest() {
        final D dto = dtosSupplier.get().iterator().next();

        helper.processExistsByIdSuccessTest(dto);
    }

    public void existsByIdFailureTest() {
        helper.processExistsByIdFailureTest();
    }

}
