package com.hurynovich.api_tester.client;

import com.hurynovich.api_tester.client.exception.ClientException;
import com.hurynovich.api_tester.dto.impl.RequestDTO;
import com.hurynovich.api_tester.dto.impl.ResponseDTO;

public interface Client {

	ResponseDTO sendRequest(RequestDTO request) throws ClientException;

}
