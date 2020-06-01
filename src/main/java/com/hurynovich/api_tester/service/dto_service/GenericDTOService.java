package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.enumeration.Status;
import com.hurynovich.api_tester.model.persistence.PersistentObject;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.exception.DTOServiceException;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class GenericDTOService<D extends AbstractDTO<I>, P extends PersistentObject<I>, I extends Serializable>
        implements DTOService<D, I> {

    private static final String EXCEPTION_MESSAGE = "Failed to instantiate class of type: ";

    private final GenericRepository<P, I> repository;

    private final DTOConverter<D, P, I> converter;

    public GenericDTOService(final @NonNull GenericRepository<P, I> repository,
                             final @NonNull DTOConverter<D, P, I> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public D create(final D d) {
        return converter.convert(repository.save(converter.convert(d)));
    }

    @Override
    public D readById(final I id) {
        return converter.convert(repository.findOne(getActiveStatusExample(id)).
                orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<D> readAll() {
        return converter.convertAllToDTO(repository.findAll(getActiveStatusExample()));
    }

    @Override
    public D update(final D d) {
        return create(d);
    }

    @Override
    public void deleteById(final I id) {
        final D d = readById(id);

        d.setStatus(Status.NOT_ACTIVE);

        update(d);
    }

    @Override
    public boolean existsById(final I id) {
        return repository.existsById(id);
    }

    protected abstract Class<P> getPersistentObjectClass();

    private Example<P> getActiveStatusExample() {
        final P persistentObject = instantiatePersistentObject();

        persistentObject.setStatus(Status.ACTIVE);

        return Example.of(persistentObject);
    }

    private Example<P> getActiveStatusExample(final I id) {
        final P persistentObject = instantiatePersistentObject();

        persistentObject.setId(id);
        persistentObject.setStatus(Status.ACTIVE);

        return Example.of(persistentObject);
    }

    private P instantiatePersistentObject() {
        final Class<P> persistentObjectClass = getPersistentObjectClass();

        try {
            return persistentObjectClass.getDeclaredConstructor().newInstance();
        } catch (final InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new DTOServiceException(EXCEPTION_MESSAGE + persistentObjectClass, ex);
        }
    }

}
