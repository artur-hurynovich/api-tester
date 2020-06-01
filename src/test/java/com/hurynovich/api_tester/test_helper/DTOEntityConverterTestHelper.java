package com.hurynovich.api_tester.test_helper;

import com.hurynovich.api_tester.converter.dto_converter.DTOConverter;
import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.PersistentObject;
import org.junit.jupiter.api.Assertions;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiConsumer;

public class DTOEntityConverterTestHelper<D extends AbstractDTO<I>, P extends PersistentObject<I>, I extends Serializable> {

    private final DTOConverter<D, P, I> converter;

    private final BiConsumer<D, P> checkConsumer;

    public DTOEntityConverterTestHelper(final DTOConverter<D, P, I> converter,
                                        final BiConsumer<D, P> checkConsumer) {
        this.converter = converter;
        this.checkConsumer = checkConsumer;
    }

    public void processConvertFromDTOTest(final D dto) {
        final P persistentObject = converter.convert(dto);

        checkConsumer.accept(dto, persistentObject);
    }

    public void processConvertFromNullDTOTest() {
        final P persistentObject = converter.convert((D) null);

        checkConsumer.accept(null, persistentObject);
    }

    public void processConvertToDTOTest(final P persistentObject) {
        final D dto = converter.convert(persistentObject);

        checkConsumer.accept(dto, persistentObject);
    }

    public void processConvertFromNullToDTOTest() {
        final D dto = converter.convert((P) null);

        checkConsumer.accept(dto, null);
    }

    public void processConvertAllFromDTOTest(final List<D> dtos) {
        final List<P> persistentObjects = converter.convertAllFromDTO(dtos);

        final int expectedSize = dtos.size();

        Assertions.assertEquals(expectedSize, persistentObjects.size());

        for (int i = 0; i < expectedSize; i++) {
            checkConsumer.accept(dtos.get(i), persistentObjects.get(i));
        }
    }

    public void processConvertAllFromNullDTOTest() {
        final List<P> persistentObjects = converter.convertAllFromDTO(null);

        Assertions.assertTrue(persistentObjects.isEmpty());
    }

    public void processConvertAllToDTOTest(final List<P> persistentObjects) {
        final List<D> dtos = converter.convertAllToDTO(persistentObjects);

        final int expectedSize = persistentObjects.size();

        Assertions.assertEquals(expectedSize, dtos.size());

        for (int i = 0; i < expectedSize; i++) {
            checkConsumer.accept(dtos.get(i), persistentObjects.get(i));
        }
    }

    public void processConvertAllFromNullToDTOTest() {
        final List<D> dtos = converter.convertAllToDTO(null);

        Assertions.assertTrue(dtos.isEmpty());
    }

}
