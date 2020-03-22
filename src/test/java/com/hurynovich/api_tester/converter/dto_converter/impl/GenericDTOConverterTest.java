package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.Identified;
import com.hurynovich.api_tester.test_helper.DTOEntityConverterTestHelper;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class GenericDTOConverterTest<D extends AbstractDTO<I>, P extends Identified<I>, I extends Serializable> {

    protected static final int DEFAULT_DTO_COUNT = 5;
    protected static final int DEFAULT_ENTITY_COUNT = DEFAULT_DTO_COUNT;

    private final Supplier<List<D>> dtosSupplier;

    private final Supplier<List<P>> entitiesSupplier;

    private final DTOEntityConverterTestHelper<D, P, I> helper;

    public GenericDTOConverterTest(final Supplier<List<D>> dtosSupplier,
                                   final Supplier<List<P>> entitiesSupplier,
                                   final Supplier<DTOConverter<D, P, I>> converterSupplier,
                                   final BiConsumer<D, P> checkConsumer) {
        this.dtosSupplier = dtosSupplier;
        this.entitiesSupplier = entitiesSupplier;

        helper = new DTOEntityConverterTestHelper<>(converterSupplier.get(), checkConsumer);
    }

    public void convertToEntityTest() {
        final D dto = dtosSupplier.get().iterator().next();

        helper.processConvertFromDTOTest(dto);
    }

    public void convertToDTOTest() {
        final P entity = entitiesSupplier.get().iterator().next();

        helper.processConvertToDTOTest(entity);
    }

    public void convertAllToEntityTest() {
        final List<D> dtos = dtosSupplier.get();

        helper.processConvertAllFromDTOTest(dtos);
    }

    public void convertAllToDTOTest() {
        final List<P> entities = entitiesSupplier.get();

        helper.processConvertAllToDTOTest(entities);
    }

}