package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

import java.io.Serializable;
import java.util.List;

public interface DTOService<D extends AbstractDTO<I>, I extends Serializable> {

    D create(D d);

    D readById(I id);

    List<D> readAll();

    D update(D d);

    void deleteById(I id);

    boolean existsById(I id);

}
