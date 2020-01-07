package com.hurynovich.api_tester.converter.generic_request_element_converter;

import com.hurynovich.api_tester.model.dto.impl.GenericRequestElementDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface GenericRequestElementConverter {

    MultiValueMap<String, String> convertToMultiValueMap(List<GenericRequestElementDTO> elements);

    HttpHeaders convertToHttpHeaders(List<GenericRequestElementDTO> elements);

}
