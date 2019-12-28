package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;
import com.hurynovich.api_tester.model.enumeration.RequestParameterType;

import java.util.Objects;

public class RequestParameterDTO extends AbstractDTO {

    private RequestParameterType type;

    private String name;

    private String value;

    public RequestParameterType getType() {
        return type;
    }

    public void setType(final RequestParameterType type) {
        this.type = type;
    }

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RequestParameterDTO that = (RequestParameterDTO) o;

        return type == that.type &&
                name.equals(that.name) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, value);
    }

}
