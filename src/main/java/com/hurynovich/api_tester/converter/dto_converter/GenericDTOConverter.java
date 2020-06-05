package com.hurynovich.api_tester.converter.dto_converter;

import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.PersistentObject;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class GenericDTOConverter<D extends AbstractDTO<I>, P extends PersistentObject<I>, I extends Serializable>
        implements DTOConverter<D, P, I> {

    private static final String EXCEPTION_MESSAGE = "Failed to instantiate class of type: ";

    private final String[] ignoreProperties;

    public GenericDTOConverter() {
        ignoreProperties = new String[0];
    }

    public GenericDTOConverter(final @NonNull String[] ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
    }

    @Override
    public P convert(final D d) {
        final Class<P> persistentObjectClass = getPersistentObjectClass();

        try {
            if (d != null) {
                final P p = persistentObjectClass.getDeclaredConstructor().newInstance();

                BeanUtils.copyProperties(d, p, ignoreProperties);

                return p;
            } else {
                return null;
            }
        } catch (final InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new ConverterException(EXCEPTION_MESSAGE + persistentObjectClass, ex);
        }
    }

    @Override
    public D convert(final P p) {
        final Class<D> dtoClass = getDTOClass();

        try {
            if (p != null) {
                final D d = dtoClass.getDeclaredConstructor().newInstance();

                BeanUtils.copyProperties(p, d, ignoreProperties);

                return d;
            } else {
                return null;
            }
        } catch (final InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new ConverterException(EXCEPTION_MESSAGE + dtoClass, ex);
        }
    }

    @Override
    public List<P> convertAllFromDTO(final Iterable<D> d) {
        if (d != null) {
            return StreamSupport.stream(d.spliterator(), false).
                    map(this::convert).
                    filter(Objects::nonNull).
                    collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<D> convertAllToDTO(final Iterable<P> p) {
        if (p != null) {
            return StreamSupport.stream(p.spliterator(), false).
                    map(this::convert).
                    filter(Objects::nonNull).
                    collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public abstract Class<D> getDTOClass();

    public abstract Class<P> getPersistentObjectClass();

}
