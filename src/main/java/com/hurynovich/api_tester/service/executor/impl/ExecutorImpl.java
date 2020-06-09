package com.hurynovich.api_tester.service.executor.impl;

import com.hurynovich.api_tester.builder.execution_log_entry_builder.ExecutionLogEntryBuilder;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.client.Client;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.execution.ExecutionResult;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.model.execution.IndexedRequestDTOWrapper;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;
import com.hurynovich.api_tester.service.executor.Executor;
import com.hurynovich.api_tester.service.request_expression_manager.RequestExpressionManager;
import com.hurynovich.api_tester.state_transition.state.StateName;
import com.hurynovich.api_tester.state_transition.state_manager.StateManager;
import com.hurynovich.api_tester.utils.RequestWrapperUtils;
import com.hurynovich.api_tester.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExecutorImpl implements Executor {

    private final Validator<ExecutionSignal> signalValidator;

    private final ExecutionHelper executionHelper;

    private final RequestExpressionManager requestExpressionManager;

    private final Client client;

    private final StateManager stateManager;

    private final ExecutionLogEntryBuilder executionLogEntryBuilder;

    public ExecutorImpl(final @NonNull @Qualifier("executorExecutionSignalValidator") Validator<ExecutionSignal> signalValidator,
                        final @NonNull ExecutionHelper executionHelper,
                        final @NonNull RequestExpressionManager requestExpressionManager,
                        final @NonNull Client client,
                        final @NonNull StateManager stateManager,
                        final @NonNull ExecutionLogEntryBuilder executionLogEntryBuilder) {
        this.signalValidator = signalValidator;
        this.executionHelper = executionHelper;
        this.requestExpressionManager = requestExpressionManager;
        this.client = client;
        this.stateManager = stateManager;
        this.executionLogEntryBuilder = executionLogEntryBuilder;
    }

    // TODO refactor
    @Override
    public void execute(final @NonNull ExecutionSignal executionSignal) {
        // TODO validation
        final ExecutionState executionState = executionHelper.updateExecutionStateCache(executionSignal);

        final ExecutionCacheKey executionCacheKey = executionSignal.getExecutionCacheKey();

        requestExpressionManager.initRequestExpressions(executionCacheKey,
                RequestWrapperUtils.unwrapRequests(executionState.getRequests()));

        final List<IndexedRequestDTOWrapper> requestWrappers = executionState.getRequests();
        ExecutionLogDTO executionLog = executionHelper.getExecutionLog(executionCacheKey);
        if (executionLog == null) {
            executionLog = new ExecutionLogDTO();
            executionLog.setDateTime(LocalDateTime.now(ZoneId.systemDefault()));
            executionLog.setEntries(new ArrayList<>());
        }

        while (executionState.getState().getName().equals(StateName.RUNNING)) {
            if (CollectionUtils.isNotEmpty(requestWrappers)) {

                final IndexedRequestDTOWrapper requestWrapper = requestWrappers.remove(0);

                final RequestDTO request = requestWrapper.getRequest();

                requestExpressionManager.applyRequestExpression(executionCacheKey, request);

                final ExecutionLogEntryDTO requestLogEntry = executionLogEntryBuilder.build(request);
                executionLog.getEntries().add(requestLogEntry);

                final ResponseDTO response = client.sendRequest(request);
                final ExecutionLogEntryDTO responseLogEntry = executionLogEntryBuilder.build(response);
                executionLog.getEntries().add(responseLogEntry);

                if (response.getHttpStatus() == HttpStatus.OK) {
                    requestExpressionManager.evaluateRequestExpression(executionCacheKey, requestWrapper.getIndex(), response);
                } else {
                    stateManager.processTransition(executionState, StateName.ERROR);
                }
            } else {
                stateManager.processTransition(executionState, StateName.FINISHED);
            }

            // TODO consider total execution invalidation on ERROR and FINISHED
            final String executionStateName = executionState.getState().getName();
            if (executionStateName.equals(StateName.ERROR) || executionStateName.equals(StateName.FINISHED)) {
                requestExpressionManager.invalidateRequestExpressions(executionCacheKey);
            }

            final ExecutionResult executionResult = new ExecutionResult();

            executionResult.setStateName(executionStateName);
            executionResult.setValidSignalNames(executionHelper.resolveValidSignalNamesOnExecution(executionState));
            executionResult.setExecutionLog(executionLog);
            // TODO send result via web-socket and save executionLog to DB
            // TODO handle ExecutionState- and ExecutionLog- caches
        }

        // TODO consider total execution invalidation on exception
    }

}
