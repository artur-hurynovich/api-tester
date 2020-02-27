package com.hurynovich.api_tester.model.entity.impl;

import com.hurynovich.api_tester.model.entity.AbstractEntity;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "apte_name_value_elements")
public class NameValueElementEntity extends AbstractEntity {

    private String name;

    private String value;

    private String expression;

    @Enumerated(EnumType.STRING)
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

}
