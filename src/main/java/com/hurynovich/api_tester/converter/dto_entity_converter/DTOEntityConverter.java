package com.hurynovich.api_tester.converter.dto_entity_converter;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.entity.AbstractEntity;

import java.util.List;

public interface DTOEntityConverter<D extends AbstractDTO, E extends AbstractEntity> {

    E convert(D d);

    D convert(E e);

    List<E> convertAllToEntity(List<D> d);

    List<D> convertAllToDTO(List<E> e);

}
