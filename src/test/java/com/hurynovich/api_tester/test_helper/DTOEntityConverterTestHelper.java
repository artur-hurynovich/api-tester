package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.PersistentObject;
import org.junit.jupiter.api.Assertions;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiConsumer;

public class DTOEntityConverterTestHelper<D extends AbstractDTO<I>, C extends PersistentObject<I>, I extends Serializable> {

    private final DTOConverter<D, C, I> converter;

    private final BiConsumer<D, C> checkConsumer;

    public DTOEntityConverterTestHelper(final DTOConverter<D, C, I> converter,
                                        final BiConsumer<D, C> checkConsumer) {
        this.converter = converter;
        this.checkConsumer = checkConsumer;
    }

    public void processConvertFromDTOTest(final D dto) {
        final C convertible = converter.convert(dto);

        checkConsumer.accept(dto, convertible);
    }

    public void processConvertFromNullDTOTest() {
        final C convertible = converter.convert((D) null);

        checkConsumer.accept(null, convertible);
    }

    public void processConvertToDTOTest(final C convertible) {
        final D dto = converter.convert(convertible);

        checkConsumer.accept(dto, convertible);
    }

    public void processConvertFromNullToDTOTest() {
        final D dto = converter.convert((C) null);

        checkConsumer.accept(dto, null);
    }

    public void processConvertAllFromDTOTest(final List<D> dtos) {
        final List<C> convertibles = converter.convertAllFromDTO(dtos);

        final int expectedSize = dtos.size();

        Assertions.assertEquals(expectedSize, convertibles.size());

        for (int i = 0; i < expectedSize; i++) {
            checkConsumer.accept(dtos.get(i), convertibles.get(i));
        }
    }

    public void processConvertAllFromNullDTOTest() {
        final List<C> convertibles = converter.convertAllFromDTO(null);

        Assertions.assertTrue(convertibles.isEmpty());
    }

    public void processConvertAllToDTOTest(final List<C> convertibles) {
        final List<D> dtos = converter.convertAllToDTO(convertibles);

        final int expectedSize = convertibles.size();

        Assertions.assertEquals(expectedSize, dtos.size());

        for (int i = 0; i < expectedSize; i++) {
            checkConsumer.accept(dtos.get(i), convertibles.get(i));
        }
    }

    public void processConvertAllFromNullToDTOTest() {
        final List<D> dtos = converter.convertAllToDTO(null);

        Assertions.assertTrue(dtos.isEmpty());
    }

}
