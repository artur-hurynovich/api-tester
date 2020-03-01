package com.hurynovich.api_tester.converter.request_element_converter;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface RequestElementConverter {

    MultiValueMap<String, String> convertToMultiValueMap(List<NameValueElementDTO> elements);

    HttpHeaders convertToHttpHeaders(List<NameValueElementDTO> elements);

    List<NameValueElementDTO> convertToRequestElements(HttpHeaders httpHeaders);

}
