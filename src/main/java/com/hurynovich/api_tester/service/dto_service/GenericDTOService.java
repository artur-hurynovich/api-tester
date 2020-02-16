package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public abstract class GenericDTOService <D extends AbstractDTO, E extends AbstractEntity, I>
        implements DTOService<D, I> {

    private final JpaRepository<E, I> repository;

    private final DTOEntityConverter<D, E> converter;

    public GenericDTOService(final @NonNull JpaRepository<E, I> repository,
                             final @NonNull DTOEntityConverter<D, E> converter) {
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
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(final I id) {
        return repository.existsById(id);
    }

}
