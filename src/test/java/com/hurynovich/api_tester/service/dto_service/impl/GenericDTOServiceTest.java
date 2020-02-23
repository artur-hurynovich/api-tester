package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.test_helper.DTOServiceTestHelper;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class GenericDTOServiceTest<D extends AbstractDTO, E extends AbstractEntity> {

    protected static final int DEFAULT_DTO_COUNT = 5;

    private final Supplier<List<D>> dtosSupplier;

    private final DTOServiceTestHelper<D, E> helper;

    public GenericDTOServiceTest(final Supplier<List<D>> dtosSupplier,
                                 final Supplier<JpaRepository<E, Long>> repositorySupplier,
                                 final Supplier<DTOEntityConverter<D, E>> converterSupplier,
                                 final BiFunction<JpaRepository<E, Long>, DTOEntityConverter<D, E>, DTOService<D, Long>> serviceBiFunction,
                                 final BiConsumer<D, D> checkConsumer) {
        this.dtosSupplier = dtosSupplier;

        final JpaRepository<E, Long> repository = repositorySupplier.get();
        final DTOEntityConverter<D, E> converter = converterSupplier.get();
        final DTOService<D, Long> service = serviceBiFunction.apply(repository, converter);

        helper = new DTOServiceTestHelper<>(repository, converter, service, checkConsumer);
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
