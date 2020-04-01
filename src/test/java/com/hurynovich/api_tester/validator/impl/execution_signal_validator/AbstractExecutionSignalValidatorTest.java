package com.hurynovich.api_tester.validator.impl.execution_signal_validator;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import com.hurynovich.api_tester.validator.impl.ExecutionCacheKeyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

import java.util.List;

public class AbstractExecutionSignalValidatorTest {

    private Validator<ExecutionCacheKey> keyValidator = new ExecutionCacheKeyValidator();

    private Validator<ExecutionSignal> signalValidator =
            new AbstractExecutionSignalValidator(keyValidator) {
                @Override
                protected void validateSignalName(final @NonNull ExecutionSignal executionSignal,
                                                  final @NonNull ValidationResult validationResult) {

                }
            };

    @Test
    public void successValidationTest() {
        final String randomSignalName = ExecutionTestHelper.getRandomSignalName();

        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(randomSignalName);

        final ValidationResult validationResult = signalValidator.validate(signal);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void executionSignalNullFailureValidationTest() {
        final ValidationResult validationResult = signalValidator.validate(null);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();

        Assertions.assertEquals(1, descriptions.size());

        Assertions.assertEquals("'executionSignal' can't be null", descriptions.get(0));
    }

    @Test
    public void keyIsNullFailureValidationTest() {
        final String randomSignalName = ExecutionTestHelper.getRandomSignalName();

        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(randomSignalName);

        signal.setExecutionCacheKey(null);

        final ValidationResult validationResult = signalValidator.validate(signal);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();

        Assertions.assertEquals(1, descriptions.size());

        Assertions.assertEquals("'executionSignal.executionCacheKey' can't be null", descriptions.get(0));
    }

}
