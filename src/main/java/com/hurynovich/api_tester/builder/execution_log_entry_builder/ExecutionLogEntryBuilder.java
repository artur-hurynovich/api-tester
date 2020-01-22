package com.hurynovich.api_tester.builder.execution_log_entry_builder;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;

public interface ExecutionLogEntryBuilder {

    ExecutionLogEntryDTO build(RequestDTO request);

    ExecutionLogEntryDTO build(ResponseDTO response);

    ExecutionLogEntryDTO build(String message);

}
