package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;

import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.function.BiConsumer;

public class DTOEntityConverterTestHelper<D extends AbstractDTO, E extends AbstractEntity> {

    private final DTOEntityConverter<D, E> converter;

    private final BiConsumer<D, E> checkConsumer;

    public DTOEntityConverterTestHelper(final DTOEntityConverter<D, E> converter,
                                        final BiConsumer<D, E> checkConsumer) {
        this.converter = converter;
        this.checkConsumer = checkConsumer;
    }

    public void processConvertToEntityTest(final List<D> dtos) {
        final D dto = dtos.iterator().next();

        final E entity = converter.convert(dto);

        checkConsumer.accept(dto, entity);
    }

    public void processConvertToDTOTest(final List<E> entities) {
        final E entity = entities.iterator().next();

        final D dto = converter.convert(entity);

        checkConsumer.accept(dto, entity);
    }

    public void processConvertAllToEntityTest(final List<D> dtos) {
        final List<E> entities = converter.convertAllToEntity(dtos);

        final int expectedSize = dtos.size();

        Assertions.assertEquals(expectedSize, entities.size());

        for (int i = 0; i < expectedSize; i++) {
            checkConsumer.accept(dtos.get(i), entities.get(i));
        }
    }

    public void processConvertAllToDTOTest(final List<E> entities) {
        final List<D> dtos = converter.convertAllToDTO(entities);

        final int expectedSize = entities.size();

        Assertions.assertEquals(expectedSize, dtos.size());

        for (int i = 0; i < expectedSize; i++) {
            checkConsumer.accept(dtos.get(i), entities.get(i));
        }
    }

}
