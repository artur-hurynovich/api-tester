package com.hurynovich.api_tester.model.entity.impl;

import com.hurynovich.api_tester.model.entity.AbstractEntity;
import com.hurynovich.api_tester.model.enumeration.RequestElementType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "apte_request_elements")
public class RequestElementEntity extends AbstractEntity {

    private String name;

    private String value;

    private String expression;

    @Enumerated(EnumType.STRING)
    private RequestElementType type;

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

    public RequestElementType getType() {
        return type;
    }

    public void setType(final RequestElementType type) {
        this.type = type;
    }

}
