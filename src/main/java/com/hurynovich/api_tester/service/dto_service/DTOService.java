package com.hurynovich.api_tester.service.dto_service;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

public interface DTOService<D extends AbstractDTO, I> {

	boolean existsById(final I id);

}
