package com.hurynovich.api_tester.utils.url;

import com.hurynovich.api_tester.utils.url.constants.UrlUtilsConstants;

public class UrlUtils {

    private UrlUtils() {
    }

    public static String clearParameters(final String url) {
        final int parametersPrefixIndex = url.indexOf(UrlUtilsConstants.PARAMETERS_PREFIX);

        if (parametersPrefixIndex != -1) {
            return url.substring(0, parametersPrefixIndex);
        } else {
            return url;
        }
    }

}
