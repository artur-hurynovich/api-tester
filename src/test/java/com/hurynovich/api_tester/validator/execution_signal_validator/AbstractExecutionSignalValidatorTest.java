package com.hurynovich.api_tester.validator.execution_signal_validator;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.test_helper.ValidatorTestHelper;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AbstractExecutionSignalValidatorTest {

    @Mock
    private Validator<ExecutionStateCacheKey> keyValidator;

    private Validator<ExecutionSignal> signalValidator;

    @BeforeEach
    public void init() {
        signalValidator =
                new AbstractExecutionSignalValidator(keyValidator) {
                    @Override
                    protected void processNotNullSignalTypeValidation(ExecutionSignal signal, ValidationResult validationResult) {

                    }
                };
    }

    @Test
    public void successValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        final ValidationResult keyValidationResult = ValidatorTestHelper.buildValidValidationResult();
        Mockito.when(keyValidator.validate(signal.getKey())).thenReturn(keyValidationResult);

        final ValidationResult validationResult = signalValidator.validate(signal);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void keyIsNullFailureValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        signal.setKey(null);

        final ValidationResult validationResult = signalValidator.validate(signal);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());

        Assertions.assertEquals("'key' can't be null", descriptions.get(0));
    }

}
