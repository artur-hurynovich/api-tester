package com.hurynovich.api_tester.converter.request_expression_unit_container_converter.impl;

import com.hurynovich.api_tester.converter.request_expression_unit_container_converter.RequestExpressionUnitContainerService;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
import com.hurynovich.api_tester.model.execution.RequestExpressionUnit;
import com.hurynovich.api_tester.model.execution.RequestExpressionUnitContainer;
import com.hurynovich.api_tester.service.exception.RequestExpressionUnitContainerServiceException;
import com.hurynovich.api_tester.utils.RequestExpressionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestExpressionUnitContainerServiceImpl implements RequestExpressionUnitContainerService {

    @Override
    public RequestExpressionUnitContainer convert(final @NonNull List<RequestDTO> requests) {
        final Map<Integer, List<String>> expressionsByIndex = new HashMap<>();

        final List<String> extractedExpressions = requests.stream().
                flatMap(request -> {
                    final List<NameValueElementDTO> nameValueElements = new ArrayList<>();

                    nameValueElements.addAll(request.getHeaders());
                    nameValueElements.addAll(request.getParameters());

                    return nameValueElements.stream();
                }).
                filter(nameValueElement -> nameValueElement.getType() == NameValueElementType.EXPRESSION).
                map(NameValueElementDTO::getExpression).
                collect(Collectors.toList());

        extractedExpressions.forEach(extractedExpression -> {
            final int index = RequestExpressionUtils.extractIndex(extractedExpression);

            if (expressionsByIndex.containsKey(index)) {
                expressionsByIndex.get(index).add(extractedExpression);
            } else {
                final List<String> expressions = new ArrayList<>();

                expressions.add(extractedExpression);

                expressionsByIndex.put(index, expressions);
            }
        });

        final Map<Integer, List<RequestExpressionUnit>> requestExpressionUnitsByIndex =
                new HashMap<>();

        expressionsByIndex.forEach((index, expressions) ->
                requestExpressionUnitsByIndex.put(index, RequestExpressionUtils.convertExpressions(expressions)));

        return new RequestExpressionUnitContainer(requestExpressionUnitsByIndex);
    }

    @Override
    public String fetchExpressionValueFromContainer(final @NonNull RequestExpressionUnitContainer requestExpressionUnitContainer,
                                                    final @NonNull String expression) {
        final String expressionValue;

        final Map<Integer, List<RequestExpressionUnit>> requestExpressionUnitsByIndex =
                requestExpressionUnitContainer.getRequestExpressionUnitsByIndex();

        final int index = RequestExpressionUtils.extractIndex(expression);

        final List<RequestExpressionUnit> requestExpressionUnits = requestExpressionUnitsByIndex.get(index);

        if (CollectionUtils.isNotEmpty(requestExpressionUnits)) {
            final List<RequestExpressionUnit> matchingRequestExpressionUnits =
                    RequestExpressionUtils.filterMatchingRequestExpressionUnits(requestExpressionUnits, expression);

            if (matchingRequestExpressionUnits.isEmpty()) {
                expressionValue = null;
            } else if (matchingRequestExpressionUnits.size() == 1) {
                expressionValue = matchingRequestExpressionUnits.iterator().next().getValue();
            } else {
                throw new RequestExpressionUnitContainerServiceException("More than one 'RequestExpressionUnit' found " +
                        "for 'expression' = '" + expression + "':\n" + matchingRequestExpressionUnits);
            }
        } else {
            expressionValue = null;
        }

        return expressionValue;
    }

}
