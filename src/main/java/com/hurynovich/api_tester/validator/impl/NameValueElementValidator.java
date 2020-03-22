package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.model.dto.impl.NameValueElementDTO;
import com.hurynovich.api_tester.model.enumeration.NameValueElementType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.validator.Validator;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NameValueElementValidator implements Validator<NameValueElementDTO> {

    @Override
    public ValidationResult validate(final @Nullable NameValueElementDTO nameValueElement) {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.setType(ValidationResultType.VALID);
        validationResult.setDescriptions(new ArrayList<>());

        if (nameValueElement == null) {
            validationResult.setType(ValidationResultType.NON_VALID);
            validationResult.getDescriptions().add("'nameValueElement' can't be null");
        } else {
            if (nameValueElement.getName() == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'nameValueElement.name' can't be null");
            }

            final NameValueElementType type = nameValueElement.getType();
            if (type == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'nameValueElement.type' can't be null");
            }

            if (type == NameValueElementType.VALUE && nameValueElement.getValue() == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'nameValueElement.value' can't be null for 'nameValueElement.type' '" +
                        type + "'");
            }

            if (type == NameValueElementType.EXPRESSION && nameValueElement.getExpression() == null) {
                validationResult.setType(ValidationResultType.NON_VALID);
                validationResult.getDescriptions().add("'nameValueElement.expression' can't be null for 'nameValueElement.type' '" +
                        type + "'");
            }
        }

        return validationResult;
    }

}
