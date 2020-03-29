package com.hurynovich.api_tester.validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionCacheKey;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

public class ExecutionCacheKeyValidatorTest {

    private Validator<ExecutionCacheKey> keyValidator = new ExecutionCacheKeyValidator();

    @Test
    public void successValidationTest() {
        final ExecutionCacheKey executionCacheKey = ExecutionTestHelper.buildExecutionCacheKey();

        final ValidationResult validationResult = keyValidator.validate(executionCacheKey);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void executionKeyNullFailureValidationTest() throws NoSuchFieldException, IllegalAccessException {
        final ExecutionCacheKey executionCacheKey = ExecutionTestHelper.buildExecutionCacheKey();

        applyExecutionKeyReflect(executionCacheKey, null);

        final ValidationResult validationResult = keyValidator.validate(executionCacheKey);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'executionCacheKey.executionKey' can't be null", descriptions.get(0));
    }

    @Test
    public void executionKeyNotValidFailureValidationTest() throws NoSuchFieldException, IllegalAccessException {
        final ExecutionCacheKey executionCacheKey = ExecutionTestHelper.buildExecutionCacheKey();

        applyExecutionKeyReflect(executionCacheKey, RandomValueGenerator.generateRandomStringLettersOnly(15));

        final ValidationResult validationResult = keyValidator.validate(executionCacheKey);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'" + executionCacheKey.getExecutionKey() +
                "' is not a valid 'executionCacheKey.executionKey'", descriptions.get(0));
    }

    private void applyExecutionKeyReflect(final ExecutionCacheKey executionCacheKey, final String executionKey)
            throws NoSuchFieldException, IllegalAccessException {
        final Field executionKeyField = executionCacheKey.getClass().getDeclaredField("executionKey");

        executionKeyField.setAccessible(true);

        executionKeyField.set(executionCacheKey, executionKey);
    }

}
