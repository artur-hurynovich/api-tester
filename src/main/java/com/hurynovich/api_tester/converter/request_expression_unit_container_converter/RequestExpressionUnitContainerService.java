package com.hurynovich.api_tester.converter.request_expression_unit_container_converter;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.execution.RequestExpressionUnitContainer;

import java.util.List;

public interface RequestExpressionUnitContainerService {

    RequestExpressionUnitContainer convert(List<RequestDTO> requests);

    String fetchExpressionValueFromContainer(RequestExpressionUnitContainer requestExpressionUnitContainer,
                                             String expression);

}
