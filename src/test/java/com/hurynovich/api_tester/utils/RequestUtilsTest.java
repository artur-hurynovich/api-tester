package com.hurynovich.api_tester.utils;

import com.hurynovich.api_tester.model.dto.impl.GenericRequestElementDTO;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.hurynovich.api_tester.utils.RequestUtils.PARAMETERS_PREFIX;
import static com.hurynovich.api_tester.utils.RequestUtils.PARAMETERS_SEPARATOR;
import static com.hurynovich.api_tester.utils.RequestUtils.PARAMETER_NAME_VALUE_SEPARATOR;

public class RequestUtilsTest {

    private static final String URL_NO_PARAMETERS = RequestTestHelper.generateRandomHttpUrl();
    private static final String URL_NO_PARAMETERS_WITH_PREFIX = URL_NO_PARAMETERS + PARAMETERS_PREFIX;
    private static final String PARAMETER_ONE = RandomValueGenerator.generateRandomStringLettersOnly(3, 10) +
            PARAMETER_NAME_VALUE_SEPARATOR +
            RandomValueGenerator.generateRandomStringLettersOnly(3, 10);
    private static final String URL_WITH_ONE_PARAMETER = URL_NO_PARAMETERS_WITH_PREFIX + PARAMETER_ONE;
    private static final String PARAMETER_TWO = RandomValueGenerator.generateRandomStringLettersOnly(3, 10) +
            PARAMETER_NAME_VALUE_SEPARATOR +
            RandomValueGenerator.generateRandomStringLettersOnly(3, 10);
    private static final String URL_WITH_TWO_PARAMETERS = URL_WITH_ONE_PARAMETER + PARAMETERS_SEPARATOR +
            PARAMETER_TWO;

    @Test
    public void clearParametersTest() {
        Assertions.assertEquals(URL_NO_PARAMETERS, RequestUtils.clearParameters(URL_NO_PARAMETERS));
        Assertions.assertEquals(URL_NO_PARAMETERS, RequestUtils.clearParameters(URL_NO_PARAMETERS_WITH_PREFIX));
        Assertions.assertEquals(URL_NO_PARAMETERS, RequestUtils.clearParameters(URL_WITH_ONE_PARAMETER));
        Assertions.assertEquals(URL_NO_PARAMETERS, RequestUtils.clearParameters(URL_WITH_TWO_PARAMETERS));
    }

    @Test
    public void appendParametersTest() {
        final List<GenericRequestElementDTO> requestParameters =
                RequestTestHelper.generateRandomGenericRequestElements(3);

        final GenericRequestElementDTO firstRequestParameter = requestParameters.get(0);
        final String firstParameter = buildUrlParameter(firstRequestParameter);

        final GenericRequestElementDTO secondRequestParameter = requestParameters.get(1);
        final String secondParameter = buildUrlParameter(secondRequestParameter);

        final GenericRequestElementDTO thirdRequestParameter = requestParameters.get(2);
        final String thirdParameter = buildUrlParameter(thirdRequestParameter);

        checkAppendParameters(URL_NO_PARAMETERS, URL_NO_PARAMETERS, Collections.emptyList());

        checkAppendParameters(URL_NO_PARAMETERS,
                URL_NO_PARAMETERS_WITH_PREFIX + secondParameter,
                Collections.singletonList(secondRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX,
                URL_NO_PARAMETERS_WITH_PREFIX + secondParameter,
                Collections.singletonList(secondRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + firstParameter,
                URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR + secondParameter,
                Collections.singletonList(secondRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR,
                URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR + secondParameter,
                Collections.singletonList(secondRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR,
                URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + secondParameter,
                Collections.singletonList(secondRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR,
                URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + secondParameter,
                Collections.singletonList(secondRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter,
                URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR + secondParameter,
                Collections.singletonList(secondRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR,
                URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR + secondParameter,
                Collections.singletonList(secondRequestParameter));

        checkAppendParameters(URL_NO_PARAMETERS,
                URL_NO_PARAMETERS_WITH_PREFIX + secondParameter + PARAMETERS_SEPARATOR + thirdParameter,
                Arrays.asList(secondRequestParameter, thirdRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX,
                URL_NO_PARAMETERS_WITH_PREFIX + secondParameter + PARAMETERS_SEPARATOR + thirdParameter,
                Arrays.asList(secondRequestParameter, thirdRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + firstParameter,
                URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR + secondParameter + PARAMETERS_SEPARATOR + thirdParameter,
                Arrays.asList(secondRequestParameter, thirdRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR,
                URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR + secondParameter + PARAMETERS_SEPARATOR + thirdParameter,
                Arrays.asList(secondRequestParameter, thirdRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR,
                URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + secondParameter + PARAMETERS_SEPARATOR + thirdParameter,
                Arrays.asList(secondRequestParameter, thirdRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter,
                URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR + secondParameter + PARAMETERS_SEPARATOR + thirdParameter,
                Arrays.asList(secondRequestParameter, thirdRequestParameter));
        checkAppendParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR,
                URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR + secondParameter + PARAMETERS_SEPARATOR + thirdParameter,
                Arrays.asList(secondRequestParameter, thirdRequestParameter));
    }

    @Test
    public void parseParametersTest() {
        final List<GenericRequestElementDTO> requestParameters =
                RequestTestHelper.generateRandomGenericRequestElements(2);

        final GenericRequestElementDTO firstRequestParameter = requestParameters.get(0);
        final String firstParameter = buildUrlParameter(firstRequestParameter);

        final GenericRequestElementDTO secondRequestParameter = requestParameters.get(1);
        final String secondParameter = buildUrlParameter(secondRequestParameter);

        checkParseParameters(URL_NO_PARAMETERS, Collections.emptyList());

        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX, Collections.emptyList());

        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR, Collections.emptyList());

        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + firstParameter,
                Collections.singletonList(firstRequestParameter));
        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR,
                Collections.singletonList(firstRequestParameter));
        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter,
                Collections.singletonList(firstRequestParameter));
        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR,
                Collections.singletonList(firstRequestParameter));

        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR + secondParameter,
                Arrays.asList(firstRequestParameter, secondRequestParameter));
        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + firstParameter + PARAMETERS_SEPARATOR + secondParameter + PARAMETERS_SEPARATOR,
                Arrays.asList(firstRequestParameter, secondRequestParameter));
        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR + secondParameter,
                Arrays.asList(firstRequestParameter, secondRequestParameter));
        checkParseParameters(URL_NO_PARAMETERS_WITH_PREFIX + PARAMETERS_SEPARATOR + firstParameter + PARAMETERS_SEPARATOR + secondParameter + PARAMETERS_SEPARATOR,
                Arrays.asList(firstRequestParameter, secondRequestParameter));
    }

    private String buildUrlParameter(final GenericRequestElementDTO requestParameter) {
        String value = requestParameter.getValue();
        if (value == null) {
            value = "";
        }

        return requestParameter.getName() + PARAMETER_NAME_VALUE_SEPARATOR + value;
    }

    private void checkAppendParameters(final String initialUrl, final String expectedUrl, final List<GenericRequestElementDTO> parameters) {
        Assertions.assertEquals(expectedUrl, RequestUtils.appendParameters(initialUrl, parameters));
    }

    private void checkParseParameters(final String url, final List<GenericRequestElementDTO> expectedParameters) {
        Assertions.assertEquals(expectedParameters, RequestUtils.parseParameters(url));
    }

}
