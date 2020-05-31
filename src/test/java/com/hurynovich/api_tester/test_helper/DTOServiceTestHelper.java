package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.enumeration.Status;
import com.hurynovich.api_tester.model.persistence.PersistentObject;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DTOServiceTestHelper<D extends AbstractDTO<I>, P extends PersistentObject<I>, I extends Serializable> {

    private final Supplier<I> idSupplier;

    private final GenericRepository<P, I> repository;

    private final DTOConverter<D, P, I> converter;

    private final DTOService<D, I> service;

    private final BiConsumer<D, D> checkConsumer;

    public DTOServiceTestHelper(final Supplier<I> idSupplier,
                                final GenericRepository<P, I> repository,
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
        Mockito.when(repository.save(Mockito.any())).
                thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        final D storedDto = service.create(dto);

        checkConsumer.accept(dto, storedDto);
    }

    public void processReadByIdSuccessTest(final D dto) {
        final P p = converter.convert(dto);

        Mockito.when(repository.findOne(Mockito.any())).thenReturn(Optional.of(p));

        service.readById(dto.getId());
    }

    public void processReadByIdFailureTest() {
        Mockito.when(repository.findOne(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.readById(idSupplier.get()));
    }

    public void processReadAllTest(final List<D> dtos) {
        final List<P> ps = converter.convertAllFromDTO(dtos);

        Mockito.when(repository.findAll(Mockito.any())).thenReturn(ps);

        final List<D> storedDtos = service.readAll();

        Assertions.assertEquals(dtos.size(), storedDtos.size());

        for (int i = 0; i < dtos.size(); i++) {
            final D dto = dtos.get(i);
            final D storedDto = storedDtos.get(i);

            checkConsumer.accept(dto, storedDto);
        }
    }

    public void processUpdateTest(final D dto, final Function<D, D> updateFunction) {
        final D updatedDto = updateFunction.apply(dto);

        Mockito.when(repository.save(Mockito.any())).
                thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        service.update(updatedDto);

        final P p = converter.convert(updatedDto);

        Mockito.when(repository.findOne(Mockito.any())).thenReturn(Optional.of(p));

        final D storedDto = service.readById(dto.getId());

        checkConsumer.accept(updatedDto, storedDto);
    }

    public void processDeleteByIdSuccessTest(final D dto) {
        final P p = converter.convert(dto);

        Mockito.when(repository.findOne(Mockito.any())).thenReturn(Optional.of(p));

        Mockito.when(repository.save(Mockito.any())).
                thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        service.deleteById(dto.getId());

        p.setStatus(Status.NOT_ACTIVE);

        Mockito.when(repository.findOne(Mockito.any())).thenReturn(Optional.of(p));

        final D d = service.readById(dto.getId());

        Assertions.assertTrue(d != null && d.getStatus() == Status.NOT_ACTIVE);
    }

    public void processDeleteByIdFailureTest() {
        Mockito.when(repository.findOne(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(idSupplier.get()));
    }

    public void processExistsByIdSuccessTest(final D dto) {
        Mockito.when(repository.existsById(dto.getId())).thenReturn(true);

        Assertions.assertTrue(service.existsById(dto.getId()));
    }

    public void processExistsByIdFailureTest() {
        final I id = idSupplier.get();

        Mockito.when(repository.existsById(id)).thenReturn(false);

        Assertions.assertFalse(service.existsById(id));
    }

}
