package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.ExecutionLogDTO;
import com.hurynovich.api_tester.model.dto.impl.ExecutionLogEntryDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExecutionLogValidator implements Validator<ExecutionLogDTO> {

    private final DTOService<UserDTO, Long> userService;

    private final Validator<ExecutionLogEntryDTO> executionLogEntryValidator;

    public ExecutionLogValidator(final @NonNull DTOService<UserDTO, Long> userService,
                                 final @NonNull Validator<ExecutionLogEntryDTO> executionLogEntryValidator) {
        this.userService = userService;
        this.executionLogEntryValidator = executionLogEntryValidator;
    }

    @Override
    public ValidationResult validate(final @Nullable ExecutionLogDTO executionLog) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        if (executionLog == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLog' can't be null");
        } else {
            validateDateTime(executionLog, validationResult);

            final Long userId = executionLog.getUserId();

            if (userId == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'executionLog.userId' can't be null");
            } else if (!userService.existsById(userId)) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("No user found for executionLog.userId '" + userId + "'");
            }

            validateExecutionLogEntries(executionLog, validationResult);
        }

        return validationResult;
    }

    private void validateDateTime(final @NonNull ExecutionLogDTO executionLog,
                                  final @NonNull ValidationResult validationResult) {
        final LocalDateTime dateTime = executionLog.getDateTime();

        if (dateTime == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLog.dateTime' can't be null");
        } else if (dateTime.isAfter(LocalDateTime.now(ZoneId.systemDefault()))) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLog.dateTime' can't be later than now");
        }
    }

    private void validateExecutionLogEntries(final @NonNull ExecutionLogDTO executionLog,
                                             final @NonNull ValidationResult validationResult) {
        final List<ExecutionLogEntryDTO> executionLogEntries = executionLog.getEntries();

        if (executionLogEntries == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLog.entries' can't be null");
        } else if (executionLogEntries.isEmpty()) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'executionLog.entries' can't be empty");
        } else {
            executionLogEntries.forEach(executionLogEntry -> {
                final ValidationResult executionLogEntryValidationResult =
                        executionLogEntryValidator.validate(executionLogEntry);

                if (executionLogEntryValidationResult.getType() == ValidationResultType.NON_VALID) {
                    validationResult.setType(ValidationResultType.NON_VALID);
                    validationResult.getDescriptions().addAll(executionLogEntryValidationResult.getDescriptions());
                }
            });
        }
    }

}
