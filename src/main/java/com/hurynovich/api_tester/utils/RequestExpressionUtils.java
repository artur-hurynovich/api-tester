package com.hurynovich.api_tester.utils;

import com.hurynovich.api_tester.model.execution.RequestExpressionUnit;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class RequestExpressionUtils {

    private static final String SEPARATOR = "#";

    private static final String REPLACE_REGEX = "\\d+" + SEPARATOR;

    private RequestExpressionUtils() {

    }

    public static int extractIndex(final @NonNull String expression) {
        final String[] splitExpression = expression.split(SEPARATOR);

        return Integer.parseInt(splitExpression[0]);
    }

    public static List<RequestExpressionUnit> convertExpressions(final @NonNull List<String> expressions) {
        return expressions.stream().map(RequestExpressionUtils::convertExpression).collect(Collectors.toList());
    }

    private static RequestExpressionUnit convertExpression(final @NonNull String expression) {
        final String resultExpression = expression.replaceFirst(REPLACE_REGEX, "");

        return new RequestExpressionUnit(resultExpression);
    }

    public static List<RequestExpressionUnit> filterMatchingRequestExpressionUnits(final @NonNull List<RequestExpressionUnit> requestExpressionUnits,
                                                                            final @NonNull String expression) {
        return requestExpressionUnits.stream().
                filter(requestExpressionUnit -> requestExpressionUnit.getExpression().
                        replaceFirst(REPLACE_REGEX, "").equals(expression)).
                collect(Collectors.toList());
    }

}
