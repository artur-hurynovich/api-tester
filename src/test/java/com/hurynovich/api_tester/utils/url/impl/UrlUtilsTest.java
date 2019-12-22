package com.hurynovich.api_tester.utils.url.impl;

import com.hurynovich.api_tester.test_helper.RequestTestHelper;
import com.hurynovich.api_tester.utils.url.UrlUtils;
import com.hurynovich.api_tester.utils.url.constants.UrlUtilsConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlUtilsTest {

	private static final String NAME_1 = "name1";
	private static final String VALUE_1 = "value1";
	private static final String NAME_2 = "name2";
	private static final String VALUE_2 = "value2";

	private static final String URL_NO_PARAMETERS = RequestTestHelper.generateRandomUrl();
	private static final String URL_WITH_PARAMETERS_PREFIX =
		URL_NO_PARAMETERS + UrlUtilsConstants.PARAMETERS_PREFIX;
	private static final String URL_WITH_ONE_PARAMETER =
		URL_NO_PARAMETERS + UrlUtilsConstants.PARAMETERS_PREFIX +
		NAME_1 + UrlUtilsConstants.PARAMETER_NAME_VALUE_SEPARATOR + VALUE_1;
	private static final String URL_WITH_TWO_PARAMETERS =
		URL_NO_PARAMETERS + UrlUtilsConstants.PARAMETERS_PREFIX +
		NAME_1 + UrlUtilsConstants.PARAMETER_NAME_VALUE_SEPARATOR + VALUE_1 +
		UrlUtilsConstants.PARAMETERS_SEPARATOR +
		NAME_2 + UrlUtilsConstants.PARAMETER_NAME_VALUE_SEPARATOR + VALUE_2;

	@Test
	public void urlUtilsImplRemoveParametersTest() {
		Assertions.assertEquals(URL_NO_PARAMETERS, UrlUtils.clearParameters(URL_NO_PARAMETERS));
		Assertions.assertEquals(URL_NO_PARAMETERS, UrlUtils.clearParameters(URL_WITH_PARAMETERS_PREFIX));
		Assertions.assertEquals(URL_NO_PARAMETERS, UrlUtils.clearParameters(URL_WITH_ONE_PARAMETER));
		Assertions.assertEquals(URL_NO_PARAMETERS, UrlUtils.clearParameters(URL_WITH_TWO_PARAMETERS));
	}

}
