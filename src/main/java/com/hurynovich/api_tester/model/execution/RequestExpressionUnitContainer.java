package com.hurynovich.api_tester.model.execution;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

public class RequestExpressionUnitContainer {

    private final Map<Integer, List<RequestExpressionUnit>> requestExpressionUnitsByIndex;

    public RequestExpressionUnitContainer(final @NonNull Map<Integer, List<RequestExpressionUnit>> requestExpressionUnitsByIndex) {
        this.requestExpressionUnitsByIndex = requestExpressionUnitsByIndex;
    }

    public Map<Integer, List<RequestExpressionUnit>> getRequestExpressionUnitsByIndex() {
        return requestExpressionUnitsByIndex;
    }

}
