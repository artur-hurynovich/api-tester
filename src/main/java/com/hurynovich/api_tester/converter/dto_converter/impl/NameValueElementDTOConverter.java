package com.hurynovich.api_tester.converter.dto_converter.impl;

import com.hurynovich.api_tester.converter.dto_converter.GenericDTOConverter;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.persistence.document.impl.NameValueElementDocument;
import org.springframework.stereotype.Service;

@Service
public class NameValueElementDTOConverter extends GenericDTOConverter<NameValueElementDTO, NameValueElementDocument, String> {

    @Override
    public Class<NameValueElementDTO> getDTOClass() {
        return NameValueElementDTO.class;
    }

    @Override
    public Class<NameValueElementDocument> getPersistentObjectClass() {
        return NameValueElementDocument.class;
    }

}
