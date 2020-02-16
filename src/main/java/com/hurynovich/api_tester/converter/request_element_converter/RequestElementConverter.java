package com.hurynovich.api_tester.converter.request_element_converter;

import com.hurynovich.api_tester.model.dto.impl.RequestElementDTO;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface RequestElementConverter {

    MultiValueMap<String, String> convertToMultiValueMap(List<RequestElementDTO> elements);

    HttpHeaders convertToHttpHeaders(List<RequestElementDTO> elements);

    List<RequestElementDTO> convertToRequestElements(HttpHeaders httpHeaders);

}
