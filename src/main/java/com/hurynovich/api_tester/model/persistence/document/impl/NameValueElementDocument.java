package com.hurynovich.api_tester.model.persistence.document.impl;

import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
import com.hurynovich.api_tester.model.persistence.document.AbstractDocument;

public class NameValueElementDocument extends AbstractDocument<String> {

    private String name;

    private NameValueElementType type;

    private String value;

    private String expression;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public NameValueElementType getType() {
        return type;
    }

    public void setType(final NameValueElementType type) {
        this.type = type;
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

}
