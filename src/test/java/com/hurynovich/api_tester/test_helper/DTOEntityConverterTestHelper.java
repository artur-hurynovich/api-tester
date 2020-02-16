package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;

import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class DTOEntityConverterTestHelper {

    public static <D extends AbstractDTO, E extends AbstractEntity> void processConvertToEntityTest(
            final Supplier<List<D>> createDTOsSupplier,
            final DTOEntityConverter<D, E> converter,
            final BiConsumer<D, E> checkConsumer) {
        final D dto = createDTOsSupplier.get().iterator().next();

        final E entity = converter.convert(dto);

        checkConsumer.accept(dto, entity);
    }

    public static <D extends AbstractDTO, E extends AbstractEntity> void processConvertToDTOTest(
            final Supplier<List<E>> createEntitiesSupplier,
            final DTOEntityConverter<D, E> converter,
            final BiConsumer<D, E> checkConsumer) {
        final E entity = createEntitiesSupplier.get().iterator().next();

        final D dto = converter.convert(entity);

        checkConsumer.accept(dto, entity);
    }

    public static <D extends AbstractDTO, E extends AbstractEntity> void processConvertAllToEntityTest(
            final Supplier<List<D>> createDTOsSupplier,
            final DTOEntityConverter<D, E> converter,
            final BiConsumer<D, E> checkConsumer) {
        final List<D> dtos = createDTOsSupplier.get();

        final List<E> entities = converter.convertAllToEntity(dtos);

        final int expectedSize = dtos.size();

        Assertions.assertEquals(expectedSize, entities.size());

        for (int i = 0; i < expectedSize; i++) {
            checkConsumer.accept(dtos.get(i), entities.get(i));
        }
    }

    public static <D extends AbstractDTO, E extends AbstractEntity> void processConvertAllToDTOTest(
            final Supplier<List<E>> createEntitiesSupplier,
            final DTOEntityConverter<D, E> converter,
            final BiConsumer<D, E> checkConsumer) {
        final List<E> entities = createEntitiesSupplier.get();

        final List<D> dtos = converter.convertAllToDTO(entities);

        final int expectedSize = entities.size();

        Assertions.assertEquals(expectedSize, dtos.size());

        for (int i = 0; i < expectedSize; i++) {
            checkConsumer.accept(dtos.get(i), entities.get(i));
        }
    }

}
