package com.hurynovich.api_tester.builder.execution_log_entry_builder;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.execution.ExecutionLogEntry;

public interface ExecutionLogEntryBuilder {

    ExecutionLogEntry build(RequestDTO requestDTO);

    ExecutionLogEntry build(ResponseDTO responseDTO);

    ExecutionLogEntry build(String message);

}