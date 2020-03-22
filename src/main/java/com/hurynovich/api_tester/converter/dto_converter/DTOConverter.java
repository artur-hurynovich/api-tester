package com.hurynovich.api_tester.converter.dto_converter;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.Identified;

import java.io.Serializable;
import java.util.List;

public interface DTOConverter<D extends AbstractDTO<I>, C extends Identified<I>, I extends Serializable> {

    C convert(D d);

    D convert(C c);

    List<C> convertAllFromDTO(Iterable<D> d);

    List<D> convertAllToDTO(Iterable<C> p);

}
