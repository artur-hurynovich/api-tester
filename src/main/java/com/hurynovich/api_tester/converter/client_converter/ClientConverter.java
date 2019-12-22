package com.hurynovich.api_tester.converter.client_converter;

import com.hurynovich.api_tester.converter.exception.ConverterException;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public interface ClientConverter<T> {

	RequestEntity<T> convert(RequestDTO request) throws ConverterException;

	ResponseDTO convert(ResponseEntity<T> response);

}
