package com.hurynovich.api_tester.service.executor.impl;

import com.hurynovich.api_tester.builder.execution_log_entry_builder.ExecutionLogEntryBuilder;
import com.hurynovich.api_tester.client.Client;
import com.hurynovich.api_tester.model.document.ExecutionLogDocument;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionStateType;
import com.hurynovich.api_tester.model.execution.ExecutionResult;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.service.executor.Executor;
import com.hurynovich.api_tester.validator.Validator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
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

    // TODO refactor
    @Override
    public void execute(final @NonNull ExecutionSignal executionSignal) {
        // TODO validation
        final ExecutionState executionState = executionHelper.updateExecutionStateCache(executionSignal);

        final List<RequestDTO> requests = executionState.getRequests();
        ExecutionLogDocument executionLog = executionHelper.getExecutionLog(executionSignal.getKey());
        if (executionLog == null) {
            executionLog = new ExecutionLogDocument();
            executionLog.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
            executionLog.setEntries(new ArrayList<>());
        }

        while (executionState.getType() == ExecutionStateType.RUNNING) {
            if (!CollectionUtils.isEmpty(requests)) {

                final RequestDTO request = requests.get(0);

                final ExecutionLogEntryDTO requestLogEntry = executionLogEntryBuilder.build(request);
                executionLog.getEntries().add(requestLogEntry);

                final ResponseDTO response = client.sendRequest(request);
                final ExecutionLogEntryDTO responseLogEntry = executionLogEntryBuilder.build(response);
                executionLog.getEntries().add(responseLogEntry);

                if (response.getStatus() != HttpStatus.OK) {
                    executionState.setType(ExecutionStateType.ERROR);
                }
            } else {
                executionState.setType(ExecutionStateType.FINISHED);
            }

            final ExecutionResult executionResult = new ExecutionResult();
            executionResult.setExecutionState(executionState);
            executionResult.setValidSignals(executionHelper.resolveValidSignalTypesOnExecution(executionState));
            executionResult.setExecutionLog(executionLog);
            // TODO send result via web-socket and save executionLog to DB
            // TODO handle ExecutionState- and ExecutionLog- caches
        }
    }

}
