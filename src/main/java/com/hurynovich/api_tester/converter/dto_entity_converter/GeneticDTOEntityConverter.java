package com.hurynovich.api_tester.converter.dto_entity_converter;

import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GeneticDTOEntityConverter<D extends AbstractDTO, E extends AbstractEntity>
        implements DTOEntityConverter<D, E> {

    private static final String EXCEPTION_MESSAGE = "Failed to perform converting due to instantiation exception";

    private final String[] ignoreProperties;

    public GeneticDTOEntityConverter() {
        ignoreProperties = new String[0];
    }

    public GeneticDTOEntityConverter(@NonNull final String[] ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
    }

    @Override
    public E convert(final D d) {
        try {
            if (d != null) {
                final E e = getEntityClass().getDeclaredConstructor().newInstance();

                BeanUtils.copyProperties(d, e, ignoreProperties);

                return e;
            } else {
                return null;
            }
        } catch (final InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new ConverterException(EXCEPTION_MESSAGE + ex);
        }
    }

    @Override
    public D convert(final E e) {
        try {
            if (e != null) {
                final D d = getDTOClass().getDeclaredConstructor().newInstance();

                BeanUtils.copyProperties(d, e, ignoreProperties);

                return d;
            } else {
                return null;
            }
        } catch (final InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new ConverterException(EXCEPTION_MESSAGE + ex);
        }
    }

    @Override
    public List<E> convertAllToEntity(final List<D> d) {
        if (!CollectionUtils.isEmpty(d)) {
            return d.stream().map(this::convert).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<D> convertAllToDTO(final List<E> e) {
        if (!CollectionUtils.isEmpty(e)) {
            return e.stream().map(this::convert).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public abstract Class<D> getDTOClass();

    public abstract Class<E> getEntityClass();

}
