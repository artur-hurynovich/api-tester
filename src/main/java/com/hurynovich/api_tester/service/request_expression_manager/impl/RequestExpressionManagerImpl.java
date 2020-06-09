package com.hurynovich.api_tester.service.request_expression_manager.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.converter.request_expression_unit_container_converter.RequestExpressionUnitContainerService;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.dto.impl.ResponseDTO;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
import com.hurynovich.api_tester.model.execution.RequestExpressionUnit;
import com.hurynovich.api_tester.model.execution.RequestExpressionUnitContainer;
import com.hurynovich.api_tester.service.exception.RequestExpressionManagerException;
import com.hurynovich.api_tester.service.request_expression_manager.RequestExpressionManager;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestExpressionManagerImpl implements RequestExpressionManager {

    private final Cache<ExecutionCacheKey, RequestExpressionUnitContainer> requestExpressionUnitContainerCache;

    private final RequestExpressionUnitContainerService requestExpressionUnitContainerService;

    public RequestExpressionManagerImpl(final @NonNull Cache<ExecutionCacheKey, RequestExpressionUnitContainer> requestExpressionUnitContainerCache,
                                        final @NonNull RequestExpressionUnitContainerService requestExpressionUnitContainerService) {
        this.requestExpressionUnitContainerCache = requestExpressionUnitContainerCache;
        this.requestExpressionUnitContainerService = requestExpressionUnitContainerService;
    }

    @Override
    public void initRequestExpressions(final @NonNull ExecutionCacheKey executionCacheKey,
                                       final @NonNull List<RequestDTO> requests) {
        final RequestExpressionUnitContainer requestExpressionUnitContainer =
                requestExpressionUnitContainerService.convert(requests);

        requestExpressionUnitContainerCache.put(executionCacheKey, requestExpressionUnitContainer);
    }

    @Override
    public void applyRequestExpression(final @NonNull ExecutionCacheKey executionCacheKey,
                                       final @NonNull RequestDTO request) {
        final RequestExpressionUnitContainer requestExpressionUnitContainer =
                requestExpressionUnitContainerCache.get(executionCacheKey);

        if (requestExpressionUnitContainer != null) {
            final List<NameValueElementDTO> nameValueElements = new ArrayList<>();

            nameValueElements.addAll(request.getHeaders());
            nameValueElements.addAll(request.getParameters());

            nameValueElements.stream().
                    filter(nameValueElement -> nameValueElement.getType() == NameValueElementType.EXPRESSION).
                    forEach(nameValueElement -> processApplyRequestExpression(requestExpressionUnitContainer, nameValueElement));
        } else {
            throw new RequestExpressionManagerException("No 'RequestExpressionUnitContainer' found for " +
                    executionCacheKey);
        }
    }

    private void processApplyRequestExpression(final @NonNull RequestExpressionUnitContainer requestExpressionUnitContainer,
                                               final @NonNull NameValueElementDTO nameValueElement) {
        final String expression = nameValueElement.getExpression();

        final String expressionValue = requestExpressionUnitContainerService.
                fetchExpressionValueFromContainer(requestExpressionUnitContainer, expression);

        if (expressionValue != null) {
            nameValueElement.setValue(expressionValue);
        } else {
            throw new RequestExpressionManagerException("No 'expressionValue' found for 'expression' = '" +
                    expression + "'");
        }
    }

    @Override
    public void evaluateRequestExpression(final @NonNull ExecutionCacheKey executionCacheKey,
                                          final int index, final @NonNull ResponseDTO response) {
        final RequestExpressionUnitContainer requestExpressionUnitContainer =
                requestExpressionUnitContainerCache.get(executionCacheKey);

        if (requestExpressionUnitContainer != null) {
            final List<RequestExpressionUnit> requestExpressionUnits = requestExpressionUnitContainer.
                    getRequestExpressionUnitsByIndex().get(index);

            if (CollectionUtils.isNotEmpty(requestExpressionUnits)) {
                requestExpressionUnits.forEach(requestExpressionUnit ->
                        processEvaluateRequestExpression(response, requestExpressionUnit));
            }
        } else {
            throw new RequestExpressionManagerException("No 'RequestExpressionUnitContainer' found for " +
                    executionCacheKey);
        }
    }

    private void processEvaluateRequestExpression(final @NonNull ResponseDTO response,
                                                  final @NonNull RequestExpressionUnit requestExpressionUnit) {
        final String responseBody = response.getBody();

        if (responseBody != null) {
            final DocumentContext documentContext = JsonPath.parse(responseBody);

            requestExpressionUnit.setValue(documentContext.read(requestExpressionUnit.getExpression()));
        } else {
            throw new RequestExpressionManagerException("Failed to evaluate request expression due to response body absence");
        }
    }

    @Override
    public void invalidateRequestExpressions(final @NonNull ExecutionCacheKey executionCacheKey) {
        requestExpressionUnitContainerCache.invalidate(executionCacheKey);
    }

}
