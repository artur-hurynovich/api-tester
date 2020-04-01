package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.utils.RequestUtils;
import com.hurynovich.api_tester.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestValidator implements Validator<RequestDTO> {

    private final Validator<NameValueElementDTO> nameValueElementValidator;

    private final UrlValidator urlValidator = new UrlValidator(RequestUtils.URL_SCHEMES);

    public RequestValidator(final @NonNull Validator<NameValueElementDTO> nameValueElementValidator) {
        this.nameValueElementValidator = nameValueElementValidator;
    }

    @Override
    public ValidationResult validate(final @Nullable RequestDTO request) {
        final ValidationResult validationResult = ValidationResult.createValidResult();

        if (request == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'request' can't be null");
        } else {
            validateMethod(request, validationResult);

            validateHeaders(request, validationResult);

            validateUrl(request, validationResult);

            validateParameters(request, validationResult);
        }

        return validationResult;
    }

    private void validateMethod(final @NonNull RequestDTO request,
                                final @NonNull ValidationResult validationResult) {
        if (request.getMethod() == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'request.method' can't be null");
        }
    }

    private void validateHeaders(final @NonNull RequestDTO request,
                                 final @NonNull ValidationResult validationResult) {
        final List<NameValueElementDTO> headers = request.getHeaders();

        if (CollectionUtils.isNotEmpty(headers)) {
            validateNameValueElements(headers, validationResult);
        }
    }

    private void validateNameValueElements(final @NonNull List<NameValueElementDTO> nameValueElements,
                                           final @NonNull ValidationResult validationResult) {
        nameValueElements.forEach(nameValueElement -> {
            final ValidationResult nameValueElementValidationResult = nameValueElementValidator.validate(nameValueElement);

            if (nameValueElementValidationResult.getType() == ValidationResultType.NON_VALID) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().addAll(nameValueElementValidationResult.getDescriptions());
            }
        });
    }

    private void validateUrl(final @NonNull RequestDTO request,
                             final @NonNull ValidationResult validationResult) {
        final String url = request.getUrl();

        if (url == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'request.url' can't be null");

        } else if (!urlValidator.isValid(url)) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'" + url + "' is not a valid 'request.url'");
        }
    }

    private void validateParameters(final @NonNull RequestDTO request,
                                    final @NonNull ValidationResult validationResult) {
        final List<NameValueElementDTO> parameters = request.getParameters();

        if (CollectionUtils.isNotEmpty(parameters)) {
            validateNameValueElements(parameters, validationResult);
        }
    }

}
