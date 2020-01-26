package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

import java.util.List;

public interface DTOService<D extends AbstractDTO, I> {

    D create(D d);

    D readById(I id);

    List<D> readAll();

    D update(D d);

    void deleteById(I i);

    boolean existsById(I id);

}
