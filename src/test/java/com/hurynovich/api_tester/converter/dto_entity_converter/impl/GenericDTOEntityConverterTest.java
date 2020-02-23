package com.hurynovich.api_tester.converter.dto_entity_converter.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;
import com.hurynovich.api_tester.test_helper.DTOEntityConverterTestHelper;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class GenericDTOEntityConverterTest<D extends AbstractDTO, E extends AbstractEntity> {

    protected static final int DEFAULT_DTO_COUNT = 5;
    protected static final int DEFAULT_ENTITY_COUNT = DEFAULT_DTO_COUNT;

    private final Supplier<List<D>> dtosSupplier;

    private final Supplier<List<E>> entitiesSupplier;

    private final DTOEntityConverterTestHelper<D, E> helper;

    public GenericDTOEntityConverterTest(final Supplier<List<D>> dtosSupplier,
                                         final Supplier<List<E>> entitiesSupplier,
                                         final Supplier<DTOEntityConverter<D, E>> converterSupplier,
                                         final BiConsumer<D, E> checkConsumer) {
        this.dtosSupplier = dtosSupplier;
        this.entitiesSupplier = entitiesSupplier;

        helper = new DTOEntityConverterTestHelper<>(converterSupplier.get(), checkConsumer);
    }

    public void convertToEntityTest() {
        final D dto = dtosSupplier.get().iterator().next();

        helper.processConvertToEntityTest(dto);
    }

    public void convertToDTOTest() {
        final E entity = entitiesSupplier.get().iterator().next();

        helper.processConvertToDTOTest(entity);
    }

    public void convertAllToEntityTest() {
        final List<D> dtos = dtosSupplier.get();

        helper.processConvertAllToEntityTest(dtos);
    }

    public void convertAllToDTOTest() {
        final List<E> entities = entitiesSupplier.get();

        helper.processConvertAllToDTOTest(entities);
    }

}
