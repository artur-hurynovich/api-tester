package com.hurynovich.api_tester.client;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;

public interface Client {

    ResponseDTO sendRequest(RequestDTO request);

}
