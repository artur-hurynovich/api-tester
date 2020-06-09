package com.hurynovich.api_tester.model.execution;

import org.springframework.lang.NonNull;

public class RequestExpressionUnit {

    private final String expression;

    private String value;

    public RequestExpressionUnit(final @NonNull String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RequestExpressionUnit{" +
                "expression='" + expression + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
