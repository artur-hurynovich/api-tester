package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;
import com.hurynovich.api_tester.service.dto_service.DTOService;

import org.junit.jupiter.api.Assertions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

public class DTOServiceTestHelper<D extends AbstractDTO, E extends AbstractEntity> {

    private final JpaRepository<E, Long> repository;

    private final DTOEntityConverter<D, E> converter;

    private final DTOService<D, Long> service;

    private final BiConsumer<D, D> checkConsumer;

    public DTOServiceTestHelper(final JpaRepository<E, Long> repository,
                                final DTOEntityConverter<D, E> converter,
                                final DTOService<D, Long> service,
                                final BiConsumer<D, D> checkConsumer) {
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

        final long id = RandomValueGenerator.generateRandomPositiveInt();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readById(id));
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

        final long id = RandomValueGenerator.generateRandomPositiveInt();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(id));
    }

    public void processExistsByIdSuccessTest(final D dto) {
        clearRepository();

        initRepository(Collections.singletonList(dto));

        Assertions.assertTrue(service.existsById(dto.getId()));
    }

    public void processExistsByIdFailureTest() {
        clearRepository();

        final long id = RandomValueGenerator.generateRandomPositiveInt();

        Assertions.assertFalse(service.existsById(id));
    }

    private void clearRepository() {
        repository.deleteAll();
    }

    private void initRepository(final List<D> dtos) {
        final List<E> entities = converter.convertAllToEntity(dtos);

        entities.forEach(repository::save);
    }

}
