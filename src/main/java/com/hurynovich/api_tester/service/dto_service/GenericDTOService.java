package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.enumeration.Status;
import com.hurynovich.api_tester.model.persistence.PersistentObject;
import com.hurynovich.api_tester.repository.GenericRepository;
import com.hurynovich.api_tester.service.exception.DTOServiceException;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class GenericDTOService<D extends AbstractDTO<I>, P extends PersistentObject<I>, I extends Serializable>
        implements DTOService<D, I> {

    private static final String EXCEPTION_MESSAGE = "Failed to instantiate class of type: ";

    private static final String ID_FIELD_NAME = "id";

    private final GenericRepository<P, I> repository;

    private final DTOConverter<D, P, I> converter;

    public GenericDTOService(final @NonNull GenericRepository<P, I> repository,
                             final @NonNull DTOConverter<D, P, I> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Transactional
    @Override
    public D create(final @NonNull D d) {
        return converter.convert(repository.save(converter.convert(d)));
    }

    @Transactional
    @Override
    public D readById(final @NonNull I id) {
        return converter.convert(repository.findOne(getActiveStatusExample(id)).
                orElseThrow(() -> new EntityNotFoundException(buildEntityNotFoundExceptionMessage(ID_FIELD_NAME, id))));
    }

    @Transactional
    @Override
    public List<D> readAll() {
        return converter.convertAllToDTO(repository.findAll(getActiveStatusExample()));
    }

    @Transactional
    public List<D> readByExample(final @NonNull Example<P> example) {
        return converter.convertAllToDTO(repository.findAll(example));
    }

    @Transactional
    @Override
    public D update(final @NonNull D d) {
        return create(d);
    }

    @Transactional
    @Override
    public void deleteById(final @NonNull I id) {
        final D d = readById(id);

        d.setStatus(Status.NOT_ACTIVE);

        update(d);
    }

    @Transactional
    @Override
    public boolean existsById(final @NonNull I id) {
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

    protected P instantiatePersistentObject() {
        final Class<P> persistentObjectClass = getPersistentObjectClass();

        try {
            return persistentObjectClass.getDeclaredConstructor().newInstance();
        } catch (final InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new DTOServiceException(EXCEPTION_MESSAGE + persistentObjectClass, ex);
        }
    }

    protected String buildEntityNotFoundExceptionMessage(final @NonNull String fieldName,
                                                         final @NonNull Serializable fieldValue) {
        final String entityName = getPersistentObjectClass().getSimpleName().replace(".class", "");

        return "No '" + entityName + "' found with '" + fieldName + "' = '" + fieldValue + "'";
    }

    protected String buildMoreThanOneEntityFoundExceptionMessage(final @NonNull String fieldName,
                                                                 final @NonNull Serializable fieldValue) {
        final String entityName = getPersistentObjectClass().getSimpleName().replace(".class", "");

        return "More than one '" + entityName + "' found with '" + fieldName + "' = '" + fieldValue + "'";
    }

}
