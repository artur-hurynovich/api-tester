package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestValidator implements Validator<RequestDTO> {

    private final Validator<NameValueElementDTO> nameValueElementValidator;

    public RequestValidator(final @NonNull Validator<NameValueElementDTO> nameValueElementValidator) {
        this.nameValueElementValidator = nameValueElementValidator;
    }

    @Override
    public ValidationResult validate(final @Nullable RequestDTO request) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        if (request == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'request' can't be null");
        } else {
            if (request.getMethod() == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'request.method' can't be null");
            }

            validateHeaders(request, validationResult);

            if (request.getUrl() == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'request.url' can't be null");
            }

            validateParameters(request, validationResult);

            if (request.getBody() == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'request.body' can't be null");
            }
        }

        return validationResult;
    }

    private void validateHeaders(final @NonNull RequestDTO request,
                                 final @NonNull ValidationResult validationResult) {
        final List<NameValueElementDTO> headers = request.getHeaders();

        if (headers == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'request.headers' can't be null");
        } else if (headers.isEmpty()) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'request.headers' can't be empty");
        } else {
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

    private void validateParameters(final @NonNull RequestDTO request,
                                    final @NonNull ValidationResult validationResult) {
        final List<NameValueElementDTO> parameters = request.getParameters();

        if (parameters == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'request.parameters' can't be null");
        } else if (parameters.isEmpty()) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'request.parameters' can't be empty");
        } else {
            validateNameValueElements(parameters, validationResult);
        }
    }

}
