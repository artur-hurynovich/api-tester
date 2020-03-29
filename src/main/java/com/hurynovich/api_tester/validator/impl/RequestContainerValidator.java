package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.RequestContainerDTO;
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
public class RequestContainerValidator implements Validator<RequestContainerDTO> {

    private final Validator<RequestDTO> requestValidator;

    public RequestContainerValidator(final @NonNull Validator<RequestDTO> requestValidator) {
        this.requestValidator = requestValidator;
    }

    @Override
    public ValidationResult validate(final @Nullable RequestContainerDTO requestContainer) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        if (requestContainer == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'requestContainer' can't be null");
        } else {
            validateName(requestContainer, validationResult);

            validateRequests(requestContainer, validationResult);
        }

        return validationResult;
    }

    private void validateName(final @NonNull RequestContainerDTO requestContainer,
                              final @NonNull ValidationResult validationResult) {
        if (requestContainer.getName() == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'requestContainer.name' can't be null");
        }
    }

    private void validateRequests(final @NonNull RequestContainerDTO requestContainer,
                                  final @NonNull ValidationResult validationResult) {
        final List<RequestDTO> requests = requestContainer.getRequests();

        if (requests == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'requestContainer.requests' can't be null");
        } else if (requests.isEmpty()) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'requestContainer.requests' can't be empty");
        } else {
            requests.forEach(request -> {
                final ValidationResult requestValidationResult = requestValidator.validate(request);

                if (requestValidationResult.getType() == ValidationResultType.NON_VALID) {
                    validationResult.setType(ValidationResultType.NON_VALID);
                    validationResult.getDescriptions().addAll(requestValidationResult.getDescriptions());
                }
            });
        }
    }

}
