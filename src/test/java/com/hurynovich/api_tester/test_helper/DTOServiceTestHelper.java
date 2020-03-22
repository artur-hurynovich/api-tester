package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.Identified;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import org.junit.jupiter.api.Assertions;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DTOServiceTestHelper<D extends AbstractDTO<I>, P extends Identified<I>, I extends Serializable> {

    private final Supplier<I> idSupplier;

    private final CrudRepository<P, I> repository;

    private final DTOConverter<D, P, I> converter;

    private final DTOService<D, I> service;

    private final BiConsumer<D, D> checkConsumer;

    public DTOServiceTestHelper(final Supplier<I> idSupplier,
                                final CrudRepository<P, I> repository,
                                final DTOConverter<D, P, I> converter,
                                final DTOService<D, I> service,
                                final BiConsumer<D, D> checkConsumer) {
        this.idSupplier = idSupplier;
        this.repository = repository;
        this.converter = converter;
        this.service = service;
        this.checkConsumer = checkConsumer;
    }

    public void processCreateTest(final D dto) {
        clearRepository();

        final D storedDto = service.create(dto);

        checkConsumer.accept(dto, storedDto);
    }

    public void processReadByIdSuccessTest(final D dto) {
        clearRepository();

        initRepository(Collections.singletonList(dto));

        service.readById(dto.getId());
    }

    public void processReadByIdFailureTest() {
        clearRepository();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readById(idSupplier.get()));
    }

    public void processReadAllTest(final List<D> dtos) {
        clearRepository();

        initRepository(dtos);

        final List<D> storedDtos = service.readAll();

        Assertions.assertEquals(dtos.size(), storedDtos.size());

        for (int i = 0; i < dtos.size(); i++) {
            final D dto = dtos.get(i);
            final D storedDto = storedDtos.get(i);

            checkConsumer.accept(dto, storedDto);
        }
    }

    public void processUpdateTest(final D dto, final Function<D, D> updateFunction) {
        clearRepository();

        initRepository(Collections.singletonList(dto));

        final D updatedDto = updateFunction.apply(dto);

        service.update(updatedDto);

        final D storedDto = service.readById(dto.getId());

        checkConsumer.accept(updatedDto, storedDto);
    }

    public void processDeleteByIdSuccessTest(final D dto) {
        clearRepository();

        initRepository(Collections.singletonList(dto));

        service.deleteById(dto.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readById(dto.getId()));
    }

    public void processDeleteByIdFailureTest() {
        clearRepository();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(idSupplier.get()));
    }

    public void processExistsByIdSuccessTest(final D dto) {
        clearRepository();

        initRepository(Collections.singletonList(dto));

        Assertions.assertTrue(service.existsById(dto.getId()));
    }

    public void processExistsByIdFailureTest() {
        clearRepository();

        Assertions.assertFalse(service.existsById(idSupplier.get()));
    }

    private void clearRepository() {
        repository.deleteAll();
    }

    private void initRepository(final List<D> dtos) {
        final List<P> persistentObjects = converter.convertAllFromDTO(dtos);

        persistentObjects.forEach(repository::save);
    }

}
