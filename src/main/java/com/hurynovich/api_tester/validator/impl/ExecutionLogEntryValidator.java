package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionLogEntryType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.utils.RequestUtils;
import com.hurynovich.api_tester.validator.Validator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExecutionLogEntryValidator implements Validator<ExecutionLogEntryDTO> {

    private final Validator<NameValueElementDTO> nameValueElementValidator;

    private final UrlValidator urlValidator = new UrlValidator(RequestUtils.URL_SCHEMES);

    public ExecutionLogEntryValidator(final @NonNull Validator<NameValueElementDTO> nameValueElementValidator) {
        this.nameValueElementValidator = nameValueElementValidator;
    }

    @Override
    public ValidationResult validate(final @Nullable ExecutionLogEntryDTO executionLogEntry) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        if (executionLogEntry == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry' can't be null");
        } else {
            final ExecutionLogEntryType type = executionLogEntry.getType();

            if (type == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'executionLogEntry.type' can't be null");
            } else {
                switch (type) {
                    case REQUEST:
                        validateRequestExecutionLogEntry(executionLogEntry, validationResult);
                        break;

                    case RESPONSE:
                        validateResponseExecutionLogEntry(executionLogEntry, validationResult);
                        break;

                    case ERROR:
                        validateErrorExecutionLogEntry(executionLogEntry, validationResult);
                        break;

                    default:
                        validationResult.setType(ValidationResultType.NON_VALID);
                        validationResult.getDescriptions().add("'" + type + "' is not a valid 'executionLogEntry.type'");
                }
            }
        }

        return validationResult;
    }

    private void validateRequestExecutionLogEntry(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                                  final @NonNull ValidationResult validationResult) {
        validateDateTime(executionLogEntry, validationResult);

        validateMethod(executionLogEntry, validationResult);

        validateHeaders(executionLogEntry, validationResult);

        validateParameters(executionLogEntry, validationResult);

        validateUrl(executionLogEntry, validationResult);

        validateBody(executionLogEntry, validationResult);
    }

    private void validateResponseExecutionLogEntry(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                                   final @NonNull ValidationResult validationResult) {
        validateDateTime(executionLogEntry, validationResult);

        validateHeaders(executionLogEntry, validationResult);

        validateStatus(executionLogEntry, validationResult);

        validateBody(executionLogEntry, validationResult);
    }

    private void validateErrorExecutionLogEntry(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                                final @NonNull ValidationResult validationResult) {
        validateDateTime(executionLogEntry, validationResult);

        validateErrorMessage(executionLogEntry, validationResult);
    }

    private void validateDateTime(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                  final @NonNull ValidationResult validationResult) {
        final LocalDateTime dateTime = executionLogEntry.getDateTime();

        if (dateTime == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.dateTime' can't be null");
        } else if (dateTime.isAfter(LocalDateTime.now(ZoneId.systemDefault()))) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.dateTime' can't be later than now");
        }
    }

    private void validateMethod(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                final @NonNull ValidationResult validationResult) {
        if (executionLogEntry.getMethod() == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.method' can't be null for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");
        }
    }

    private void validateHeaders(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                 final @NonNull ValidationResult validationResult) {
        final List<NameValueElementDTO> headers = executionLogEntry.getHeaders();

        if (headers == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.headers' can't be null for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");
        } else if (headers.isEmpty()) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.headers' can't be empty for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");
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

    private void validateParameters(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                    final @NonNull ValidationResult validationResult) {
        final List<NameValueElementDTO> parameters = executionLogEntry.getParameters();

        if (parameters == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.parameters' can't be null for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");
        } else if (parameters.isEmpty()) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.parameters' can't be empty for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");
        } else {
            validateNameValueElements(parameters, validationResult);
        }
    }

    private void validateUrl(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                             final @NonNull ValidationResult validationResult) {
        final String url = executionLogEntry.getUrl();

        if (url == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.url' can't be null for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");

        } else if (!urlValidator.isValid(url)) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'" + url + "' is not a valid 'executionLogEntry.url'");
        }
    }

    private void validateStatus(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                final @NonNull ValidationResult validationResult) {
        if (executionLogEntry.getStatus() == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.status' can't be null for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");
        }
    }

    private void validateBody(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                              final @NonNull ValidationResult validationResult) {
        if (executionLogEntry.getBody() == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.body' can't be null for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");
        }
    }

    private void validateErrorMessage(final @NonNull ExecutionLogEntryDTO executionLogEntry,
                                      final @NonNull ValidationResult validationResult) {
        if (executionLogEntry.getErrorMessage() == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLogEntry.errorMessage' can't be null for 'executionLogEntry.type' '" +
                    executionLogEntry.getType() + "'");
        }
    }

}
