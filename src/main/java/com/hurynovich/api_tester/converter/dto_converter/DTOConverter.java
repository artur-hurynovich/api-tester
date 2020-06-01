package com.hurynovich.api_tester.converter.dto_converter;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.persistence.PersistentObject;

import java.io.Serializable;
import java.util.List;

public interface DTOConverter<D extends AbstractDTO<I>, P extends PersistentObject<I>, I extends Serializable> {

    P convert(D d);

    D convert(P p);

    List<P> convertAllFromDTO(Iterable<D> d);

    List<D> convertAllToDTO(Iterable<P> p);

}
