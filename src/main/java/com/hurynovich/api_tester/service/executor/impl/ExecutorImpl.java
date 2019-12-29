package com.hurynovich.api_tester.service.executor.impl;

import com.hurynovich.api_tester.builder.execution_log_entry_builder.ExecutionLogEntryBuilder;
import com.hurynovich.api_tester.client.Client;
import com.hurynovich.api_tester.client.exception.ClientException;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionLogEntry;
import com.hurynovich.api_tester.model.execution.ExecutionResult;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.service.executor.Executor;
import com.hurynovich.api_tester.validator.Validator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ExecutorImpl implements Executor {

    private final Validator<ExecutionSignal> signalValidator;

    private final ExecutionHelper executionHelper;

    private final Client client;

    private final ExecutionLogEntryBuilder executionLogEntryBuilder;

    public ExecutorImpl(final @NonNull @Qualifier("executorExecutionSignalValidator") Validator<ExecutionSignal> signalValidator,
                        final @NonNull ExecutionHelper executionHelper,
                        final @NonNull Client client,
                        final @NonNull ExecutionLogEntryBuilder executionLogEntryBuilder) {
        this.signalValidator = signalValidator;
        this.executionHelper = executionHelper;
        this.client = client;
        this.executionLogEntryBuilder = executionLogEntryBuilder;
    }

    @Override
    public void execute(final @NonNull ExecutionSignal executionSignal) {
        // TODO validation
        final ExecutionState executionState = executionHelper.updateExecutionStateCache(executionSignal);

        while (executionState.getType() == ExecutionStateType.RUNNING) {
            final List<RequestDTO> requests = executionState.getRequests();

            if (!CollectionUtils.isEmpty(requests)) {
                final ExecutionResult executionResult = sendRequest(requests.remove(0));

                // TODO send result by web-socket and save to DB (rename to ExecutionResultDTO
            } else {

            }

            // TODO handle result and set new state if necessary
        }
    }

    private ExecutionResult sendRequest(final @NonNull RequestDTO request) {
        final ExecutionResult executionResult = new ExecutionResult();
        final ExecutionLogEntry requestLogEntry = executionLogEntryBuilder.build(request);
        executionResult.addExecutionLogEntry(requestLogEntry);

        try {
            final ResponseDTO response = client.sendRequest(request);
            final ExecutionLogEntry responseLogEntry = executionLogEntryBuilder.build(response);
            executionResult.addExecutionLogEntry(responseLogEntry);
        } catch (final ClientException e) {
            final ExecutionLogEntry errorLogEntry = executionLogEntryBuilder.build("Failed to send request: " + e);
            executionResult.addExecutionLogEntry(errorLogEntry);
        }

        return executionResult;
    }

}
