package com.hurynovich.api_tester.builder.execution_log_entry_builder;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.execution.ExecutionLogEntry;

public interface ExecutionLogEntryBuilder {

    ExecutionLogEntry build(RequestDTO request);

    ExecutionLogEntry build(ResponseDTO response);

    ExecutionLogEntry build(String message);

}
