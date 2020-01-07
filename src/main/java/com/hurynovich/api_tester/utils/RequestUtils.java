package com.hurynovich.api_tester.utils;

import com.hurynovich.api_tester.model.dto.impl.RequestParameterDTO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RequestUtils {

    public static final String PARAMETERS_PREFIX = "?";
    public static final String PARAMETERS_SEPARATOR = "&";
    public static final String PARAMETER_NAME_VALUE_SEPARATOR = "=";

    private RequestUtils() {

    }

    public static String clearParameters(final @NonNull String url) {
        final int parametersPrefixIndex = url.indexOf(PARAMETERS_PREFIX);

        if (parametersPrefixIndex != -1) {
            return url.substring(0, parametersPrefixIndex);
        } else {
            return url;
        }
    }

    public static String appendParameters(final @NonNull String url,
                                          final @NonNull List<RequestParameterDTO> parameters) {
        final StringBuilder urlBuilder = new StringBuilder(url);

        if (!parameters.isEmpty()) {
            if (requiresParametersPrefix(url)) {
                urlBuilder.append(PARAMETERS_PREFIX);
            } else if (requiresParametersSeparator(url)) {
                urlBuilder.append(PARAMETERS_SEPARATOR);
            }

            parameters.forEach(parameter -> {
                urlBuilder.append(parameter.getName());
                urlBuilder.append(PARAMETER_NAME_VALUE_SEPARATOR);

                final String value = parameter.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    urlBuilder.append(value);
                }

                urlBuilder.append(PARAMETERS_SEPARATOR);
            });

            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }

        return urlBuilder.toString();
    }

    private static boolean requiresParametersPrefix(final @NonNull String url) {
        return !url.contains(PARAMETERS_PREFIX);
    }

    private static boolean requiresParametersSeparator(final @NonNull String url) {
        return !url.endsWith(PARAMETERS_PREFIX) && !url.endsWith(PARAMETERS_SEPARATOR);
    }

    public static List<RequestParameterDTO> parseParameters(final @NonNull String url) {
        final List<RequestParameterDTO> requestParameters;

        final int parametersPrefixIndex = url.indexOf(PARAMETERS_PREFIX);

        if (parametersPrefixIndex != -1 && url.length() > parametersPrefixIndex + 1) {
            final String parametersString = url.substring(parametersPrefixIndex + 1);

            final String[] parameters = parametersString.split(PARAMETERS_SEPARATOR);
            requestParameters = Arrays.stream(parameters).
                    filter(parameter ->
                            StringUtils.isNotEmpty(parameter) && !parameter.startsWith(PARAMETER_NAME_VALUE_SEPARATOR)).
                    map(RequestUtils::buildParameter).
                    collect(Collectors.toList());
        } else {
            requestParameters = Collections.emptyList();
        }

        return requestParameters;
    }

    private static RequestParameterDTO buildParameter(final @NonNull String parameter) {
        final RequestParameterDTO requestParameter = new RequestParameterDTO();

        final String[] parameterEntry = parameter.split(PARAMETER_NAME_VALUE_SEPARATOR);
        requestParameter.setName(parameterEntry[0]);

        if (parameterEntry.length == 2) {
            requestParameter.setValue(parameterEntry[1]);
        }

        requestParameter.setType(GenericRequestElementType.VALUE);

        return requestParameter;
    }

}
