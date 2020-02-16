package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
import com.hurynovich.api_tester.utils.ObjectUtils;

public class NameValueElementDTO extends AbstractDTO {

    private String name;

    private String value;

    private String expression;

    private NameValueElementType type;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(final String expression) {
        this.expression = expression;
    }

    public NameValueElementType getType() {
        return type;
    }

    public void setType(final NameValueElementType type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final NameValueElementDTO that = (NameValueElementDTO) o;
        return ObjectUtils.EqualsChecker.getInstance().
                with(name, that.getName()).
                with(value, that.getValue()).
                with(expression, that.getExpression()).
                with(type, that.getType()).
                check();
    }

    @Override
    public int hashCode() {
        return ObjectUtils.HashCodeCalculator.getInstance().
                with(name).
                with(value).
                with(expression).
                with(type).
                calculate();
    }

}
