package com.hurynovich.api_tester.converter.dto_converter;

import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.Identified;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class GenericDTOConverter<D extends AbstractDTO<I>, C extends Identified<I>, I extends Serializable>
        implements DTOConverter<D, C, I> {

    private static final String EXCEPTION_MESSAGE = "Failed to perform converting due to instantiation exception";

    private final String[] ignoreProperties;

    public GenericDTOConverter() {
        ignoreProperties = new String[0];
    }

    public GenericDTOConverter(final @NonNull String[] ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
    }

    @Override
    public C convert(final D d) {
        try {
            if (d != null) {
                final C c = getConvertibleClass().getDeclaredConstructor().newInstance();

                BeanUtils.copyProperties(d, c, ignoreProperties);

                return c;
            } else {
                return null;
            }
        } catch (final InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new ConverterException(EXCEPTION_MESSAGE + ex);
        }
    }

    @Override
    public D convert(final C c) {
        try {
            if (c != null) {
                final D d = getDTOClass().getDeclaredConstructor().newInstance();

                BeanUtils.copyProperties(c, d, ignoreProperties);

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
    public List<C> convertAllFromDTO(final Iterable<D> d) {
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
    public List<D> convertAllToDTO(final Iterable<C> p) {
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

    public abstract Class<C> getConvertibleClass();

}
