package com.hurynovich.api_tester.converter.dto_entity_converter;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;

import java.util.Collection;
import java.util.List;

public interface DTOEntityConverter<D extends AbstractDTO, E extends AbstractEntity> {

    E convertToEntity(D d);

    D convertToDTO(E e);

    List<E> convertAllToEntity(Collection<D> d);

    List<D> convertAllToDTO(Collection<E> e);

}
