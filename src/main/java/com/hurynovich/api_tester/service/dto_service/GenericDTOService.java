package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.Identified;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDTOService<D extends AbstractDTO<I>, C extends Identified<I>, I extends Serializable>
        implements DTOService<D, I> {

    private final CrudRepository<C, I> repository;

    private final DTOConverter<D, C, I> converter;

    public GenericDTOService(final @NonNull CrudRepository<C, I> repository,
                             final @NonNull DTOConverter<D, C, I> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public D create(final D d) {
        return converter.convert(repository.save(converter.convert(d)));
    }

    @Override
    public D readById(final I id) {
        return converter.convert(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<D> readAll() {
        return converter.convertAllToDTO(repository.findAll());
    }

    @Override
    public D update(final D d) {
        return create(d);
    }

    @Override
    public void deleteById(final I id) {
        if (existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public boolean existsById(final I id) {
        return repository.existsById(id);
    }

}
