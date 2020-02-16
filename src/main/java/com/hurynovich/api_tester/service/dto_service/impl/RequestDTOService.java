package com.hurynovich.api_tester.service.dto_service.impl;

import com.hurynovich.api_tester.converter.dto_entity_converter.DTOEntityConverter;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.entity.impl.RequestEntity;
import com.hurynovich.api_tester.service.dto_service.DTOService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestDTOService implements DTOService<RequestDTO, Long> {

    private final JpaRepository<RequestEntity, Long> requestRepository;

    private final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter;

    public RequestDTOService(final JpaRepository<RequestEntity, Long> requestRepository,
                             final DTOEntityConverter<RequestDTO, RequestEntity> requestConverter) {
        this.requestRepository = requestRepository;
        this.requestConverter = requestConverter;
    }

    @Override
    public RequestDTO create(final RequestDTO requestDTO) {
        return requestConverter.convert(requestRepository.save(requestConverter.convert(requestDTO)));
    }

    @Override
    public RequestDTO readById(final Long id) {
        return requestConverter.convert(requestRepository.findById(id).orElse(null));
    }

    @Override
    public List<RequestDTO> readAll() {
        return requestConverter.convertAllToDTO(requestRepository.findAll());
    }

    @Override
    public RequestDTO update(final RequestDTO requestDTO) {
        return create(requestDTO);
    }

    @Override
    public void deleteById(final Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public boolean existsById(final Long id) {
        return requestRepository.existsById(id);
    }

}
