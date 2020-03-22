package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

@ExtendWith({MockitoExtension.class})
public class ExecutionStateCacheKeyValidatorTest {

    private Validator<ExecutionStateCacheKey> keyValidator = new ExecutionStateCacheKeyValidator();

    @Test
    public void successValidationTest() {
        final ExecutionStateCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        final ValidationResult validationResult = keyValidator.validate(key);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void executionKeyNullFailureValidationTest() throws NoSuchFieldException, IllegalAccessException {
        final ExecutionStateCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        applyExecutionKeyReflect(key, null);

        final ValidationResult validationResult = keyValidator.validate(key);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionKey' can't be null", descriptions.get(0));
    }

    @Test
    public void executionKeyNotValidFailureValidationTest() throws NoSuchFieldException, IllegalAccessException {
        final ExecutionStateCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        applyExecutionKeyReflect(key, RandomValueGenerator.generateRandomStringLettersOnly(15));

        final ValidationResult validationResult = keyValidator.validate(key);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionKey' is not valid", descriptions.get(0));
    }

    private void applyExecutionKeyReflect(final ExecutionStateCacheKey key, final String executionKey)
            throws NoSuchFieldException, IllegalAccessException {
        final Field executionKeyField = key.getClass().getDeclaredField("executionKey");

        executionKeyField.setAccessible(true);

        executionKeyField.set(key, executionKey);
    }

}
